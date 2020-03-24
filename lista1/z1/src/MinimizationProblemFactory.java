class MinimizationProblemFactory {
    static MinimizationProblem getMinimizationProblem(int functionNum) {
        if(functionNum == 1) {
            return new FunctionG();
        } else {
            return new FunctionH();
        }
    }
}
