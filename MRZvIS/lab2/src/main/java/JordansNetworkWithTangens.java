import Jama.Matrix;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = NetworkSerializer.class)
@JsonDeserialize(using = NetworkDeserializer.class)
public class JordansNetworkWithTangens implements RecurentNetwork {
    private final int inputSize;
    private final int outputLayerSize;
    private final int contextSize;
    private final int hiddenLayerSize;
    protected Matrix firstW;
    protected Matrix secondW;
    private Matrix context;
    private ActivationFunction function = new TangensFunction();
    private Matrix secondActivationOutput;
    private Matrix secondSynapticOutput;
    private Matrix firstActivationOutput;
    private Matrix firstSynapticOutput;
    private Matrix input;
    private boolean contextFreeze;

    public JordansNetworkWithTangens(int inputSize) {
        this.inputSize = inputSize;
        outputLayerSize = 1;
        hiddenLayerSize = inputSize;
        contextSize = outputLayerSize;
        initWeights();
    }

    public void freezeContext() {
        contextFreeze = true;
    }

    public void unfreezeContext() {
        contextFreeze = false;
    }

    private void initWeights() {
        firstW = Matrix.random(inputSize + contextSize, inputSize)
                .times(2.)
                .minus(new Matrix(inputSize + contextSize, inputSize, 1.))
                .times(0.25);
        secondW = Matrix.random(inputSize, outputLayerSize)
                .times(2.)
                .minus(new Matrix(inputSize, outputLayerSize, 1.))
                .times(0.25);
        context = new Matrix(contextSize, 1);
    }

    private Matrix applyFunction(Matrix matrix) {
        Matrix outputMatrix = new Matrix(matrix.getRowDimension(), matrix.getColumnDimension());
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                outputMatrix.set(i, j, function.getValue(matrix.get(i, j)));
            }
        }
        return outputMatrix;
    }

    private Matrix applyDerivative(Matrix matrix) {
        Matrix outputMatrix = new Matrix(matrix.getRowDimension(), matrix.getColumnDimension());
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                outputMatrix.set(i, j, function.getDerivativeValue((matrix.get(i, j))));
            }
        }
        return outputMatrix;
    }


    /*
    * in: {{<vectorp><lace to context signals>}}>
    * */
    public Matrix straightPropagation(Matrix vector) {
        if (vector.getColumnDimension() != inputSize + contextSize) {
            throw new IllegalArgumentException("{{<vectorp><place to context signals>}}, expected " +
                    (inputSize + contextSize) + " size, result " + vector.getColumnDimension());
        }
        vector.setMatrix(0, 0, inputSize, inputSize + contextSize - 1, context);
        input = vector.copy();
        firstSynapticOutput = input.times(firstW);
        firstActivationOutput = applyFunction(firstSynapticOutput);
        secondSynapticOutput = firstActivationOutput.times(secondW);
        secondActivationOutput = applyFunction(secondSynapticOutput);
        if (!contextFreeze) context = secondActivationOutput.copy();
        return secondActivationOutput;
    }

    public void backPropagate(Matrix outputDifference, double teachingStep) {
        Matrix secondActivationDerivation = new Matrix(1, outputLayerSize, 1.)
                .minus(secondActivationOutput.arrayTimes(secondActivationOutput));

        Matrix hiddenLayerDifference = outputDifference
                .arrayTimesEquals(secondActivationDerivation)
                .times(secondW.transpose());
        Matrix firstActivationDerivation = new Matrix(1, hiddenLayerSize, 1.)
                .minus(firstActivationOutput.arrayTimes(firstActivationOutput));

        Matrix firstWeightsCorrection = input
                .transpose()
                .times(hiddenLayerDifference.arrayTimes(firstActivationDerivation))
                .times(teachingStep);
        firstW = firstW.minusEquals(firstWeightsCorrection);

        Matrix secondWeightsCorrection = firstActivationOutput
                .transpose()
                .times(outputDifference.arrayTimes(secondActivationDerivation))
                .times(teachingStep);
        secondW = secondW.minusEquals(secondWeightsCorrection);
    }

    @Override
    public void resetContext() {
        context = new Matrix(1, contextSize, 0);
    }
}
