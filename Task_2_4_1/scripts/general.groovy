evaluation {
    taskScore = 1.0
    softDeadLinePenalty = -0.5
    hardDeadLinePenalty = -0.5
    jacocoPercentage = 80
    jacocoScore = 1.0
    checkStyleScore = 1.0
    buildScore = 1.0
    docScore = 1.0
}

git {
    repoLinkPrefix = 'https://github.com/' // possible to switch to ssh
    repoLinkPostfix = '.git'
    defaultRepository = 'oop'
    docsBranch = 'gh-pages'
    defaultBranch = 'main'
}