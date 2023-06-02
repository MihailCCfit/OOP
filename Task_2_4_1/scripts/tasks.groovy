
branchPattern = 'task-$1-$2-$3'
folderPattern = 'Task_$1_$2_$3'
tasks {
    createTask ('Heap sort', 1, 1, 1)
    createTask 'Stack', 1, 2, 1
    createTask 'Tree', 1, 2, 2
    createTask 'Graph', 1, 2, 3
    createTask 'Substring finder', 1, 3, 1
    task 'Record book', 'Task_1_4_1', 'task-1-4-1'
    createTask('Calculator', 1, 2, 3){
        points = 2.0
    }
    task 'Notebook', 'Task_1_5_2', 'task-1-5-2'
    task('Prime numbers finder', 'Task_2_1_1', 'Task-2-1-1') {
        runTests = false
    }
    task('Prime numbers finder', 'Task_2_2_1', 'Task-2-2-1') {
        runTests = false
    }
    createTask('Javafx snake game', 2, 3, 1) {
        points = 2.0
    }
}