import matplotlib.pyplot as plt
import numpy as np

file = open('C:\\Users\\Sevrin\\Documents\\COURS\\ESIREM\\5A\\SystemeIntelligent\\TP\\GIT\\IA_With_JMetal\\Pareto.txt', "r")


x = []
y = []
line = file.readline()
while line: 
    tab = line.split(' ')
    for i in range(len(tab)) :
        if i%2 == 0 :
            x.append(float(tab[i]))
        else:
            y.append(float(tab[i]))
    line = file.readline()
file.close()

plt.plot(x, y,'go')
plt.title('Front de Pareto')
plt.xlabel('x (f1)')
plt.ylabel('y (f2)')
plt.show()