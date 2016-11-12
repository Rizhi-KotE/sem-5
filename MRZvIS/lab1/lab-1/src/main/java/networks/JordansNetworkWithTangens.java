package teaching;

import Jama.Matrix;

public class JordansNetworkWithTangens {
    private Matrix firstW;

    private Matrix secondW;

    private Matrix context;

    private int inputSize;

    private int hiddenLayerSize;

    private int outputLayerSize;

    private int contextSize;

    public JordansNetworkWithTangens(int inputSize, int hiddenLayerSize) {
        this.inputSize = inputSize;
        this.hiddenLayerSize = hiddenLayerSize;
        outputLayerSize = 1;
        contextSize = outputLayerSize;
        initWeights();
    }

    private void initWeights() {
        firstW = Matrix.random(inputSize + contextSize, hiddenLayerSize)
                .times(2.)
                .minus(new Matrix(inputSize + contextSize, hiddenLayerSize, 1.))
                .times(0.25);
        secondW = Matrix.random(hiddenLayerSize, outputLayerSize)
                .times(2.)
                .minus(new Matrix(hiddenLayerSize, outputLayerSize, 1.))
                .times(0.25);
        context = new Matrix(contextSize, 1);
    }

    /*
    * in: {{<vectorp><lace to context signals>}}>
    * */
    public Matrix modifyInput(Matrix vector) throws IllegalArgumentException {
        vector.setMatrix(0, 0, inputSize, inputSize + contextSize - 1, context);
        return vector;
    }

    public Matrix straightPropagation(Matrix vector){
        if (vector.getColumnDimension() != inputSize + contextSize) {
            throw new IllegalArgumentException("{{<vectorp><lace to context signals>}}");
        }
        modifyInput(new Matrix(vector.getArray()));
        Matrix firstSynapticFunction = vector.times(firstW);
    }
}
