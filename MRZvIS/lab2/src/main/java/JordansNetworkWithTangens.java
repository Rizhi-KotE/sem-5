

import Jama.Matrix;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

@JsonSerialize(using = NetworkSerializer.class)
@JsonDeserialize(using = NetworkDeserializer.class)
public class JordansNetworkWithTangens {
    private Matrix firstW;

    private Matrix secondW;

    private Matrix context;

    private final int inputSize;

    private final int outputLayerSize;

    private final int contextSize;

    private final int hiddenLayerSize;

    private ActivationFunction function = new TangensFunction();
    private Matrix secondActivationOutput;
    private Matrix secondSynapticOutput;
    private Matrix firstActivationOutput;
    private Matrix firstSynapticOutput;
    private Matrix input;


    public JordansNetworkWithTangens(int inputSize) {
        this.inputSize = inputSize;
        outputLayerSize = 1;
        hiddenLayerSize = inputSize;
        contextSize = outputLayerSize;
        initWeights();
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
            throw new IllegalArgumentException("{{<vectorp><lace to context signals>}}");
        }
        vector.setMatrix(0, 0, inputSize, inputSize + contextSize - 1, context);
        input = vector.copy();
        firstSynapticOutput = input.times(firstW);
        firstActivationOutput = applyFunction(firstSynapticOutput);
        secondSynapticOutput = firstActivationOutput.times(secondW);
        secondActivationOutput = applyFunction(secondSynapticOutput);
        context = secondActivationOutput.copy();
        return secondActivationOutput;
    }

    public void backPropagate(Matrix outputDifference, double teachingStep) {
        Matrix outputTangensSquare = secondActivationOutput.arrayTimesEquals(secondActivationOutput);
        Matrix outputTangensDerivate = new Matrix(1, outputLayerSize, 1.).minusEquals(outputTangensSquare);
        Matrix outputLayerCorrection = outputDifference
                .times(outputTangensDerivate)
                .times(firstActivationOutput)
                .times(teachingStep)
                .transpose();
        Matrix hiddenTangensSquare = firstActivationOutput.arrayTimesEquals(firstActivationOutput);
        Matrix hiddenTangensDerivate = new Matrix(1, hiddenLayerSize, 1.).minusEquals(hiddenTangensSquare);
        Matrix hiddenDifference = outputDifference
                .times(outputTangensDerivate)
                .times(secondW.transpose());
        secondW = secondW.plusEquals(outputLayerCorrection);
        Matrix hiddenLayerCorrection = hiddenDifference
                .arrayTimesEquals(hiddenTangensDerivate)
                .transpose()
                .times(input)
                .times(teachingStep);
        firstW = firstW.plus(hiddenLayerCorrection.transpose());
    }
}
