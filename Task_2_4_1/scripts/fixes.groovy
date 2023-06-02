forStudent("MihailCCfit") {
    changeBranch(['Task_1_2_1': 'task-1-2-1'])
    changeFolder 'Task_1_2_1', 'Task_1_2_1'
    changeBranchPattern 'task-$1-$2-$3'
    changeFolderPattern 'Task_$1_$2_$3'
    changeExtraScore([Task_1_5_1: 0.1, Task_2_3_1: 0.1])
}