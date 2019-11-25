package perceptron

class Examples {
    static def inputs = List.of(
            new ExampleNumber(0, [1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1] as int),
            new ExampleNumber(0, [1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1] as int),
            new ExampleNumber(1, [0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0] as int),
            new ExampleNumber(1, [0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0] as int),
            new ExampleNumber(2, [0, 1, 1] as int),
            new ExampleNumber(2, [0, 1, 1] as int),
            new ExampleNumber(3, [0, 1, 1] as int),
            new ExampleNumber(3, [0, 1, 1] as int),
            new ExampleNumber(4, [0, 1, 1] as int),
            new ExampleNumber(4, [0, 1, 1] as int),
            new ExampleNumber(5, [0, 1, 1] as int),
            new ExampleNumber(5, [0, 1, 1] as int),
            new ExampleNumber(6, [0, 1, 1] as int),
            new ExampleNumber(6, [0, 1, 1] as int),
            new ExampleNumber(7, [0, 1, 1] as int),
            new ExampleNumber(7, [0, 1, 1] as int),
            new ExampleNumber(8, [0, 1, 1] as int),
            new ExampleNumber(8, [0, 1, 1] as int),
            new ExampleNumber(9, [0, 1, 1] as int),
            new ExampleNumber(9, [0, 1, 1] as int),
            new ExampleNumber(10, [0, 1, 1] as int),
            new ExampleNumber(10, [0, 1, 1] as int)
    )
}

class ExampleNumber {

    def number;
    int[] representation

    ExampleNumber(number, int[] representation) {
        this.number = number
        this.representation = representation
    }
}