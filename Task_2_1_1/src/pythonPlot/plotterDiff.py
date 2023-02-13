import matplotlib.pyplot as plt
import numpy as np
import os


def save(name='', fmt='png'):
    pwd = os.getcwd()
    iPath = './pictures/{}'.format(fmt)
    if not os.path.exists(iPath):
        os.mkdir(iPath)
    os.chdir(iPath)
    plt.savefig('{}.{}'.format(name, fmt))
    os.chdir(pwd)
    plt.close()


def readData(path="results.txt"):
    with open(path, "r") as results_file:
        results_file



plt.plot(x, f, label=u'Сумма cos и sin')
plt.plot(x, y, label=u'Произведение cos и sin')
plt.plot(xz, yz, label=u'Кардиоида')

plt.grid(True)
plt.xlabel(u'Число чисел')
plt.ylabel(u'Пропускная способность')
plt.title(u'Графики пропускных способностей')

plt.legend()  # легенда для всего рисунка fig

save('pic_12_1_1', fmt='png')

plt.show()
