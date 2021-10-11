import matplotlib.pyplot as plt
import numpy as np

file = open('C:\\Users\\Sevrin\\Documents\\COURS\\ESIREM\\5A\\SystemeIntelligent\\TP\\GIT\\IA_With_JMetal\\pareto.txt', "r")


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
plt.title('Nuage de points avec Matplotlib')
plt.xlabel('x')
plt.ylabel('y')
plt.show()