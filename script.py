import matplotlib.pyplot as plt
import numpy as np

# Ouvrir le fichier en lecture seule
file = open('C:\\Users\\Sevrin\\Documents\\ESIREM\\Systeme Intelligent\\TP\\TP2\\VAR', "r")
file2 = open('C:\\Users\\Sevrin\\Documents\\ESIREM\\Systeme Intelligent\\TP\\TP2\\cibles3.txt', "r")

x = []
y = []
line = file.readlines()[-1]
while line: 
    tab = line.split(' ')
    for i in range(len(tab)-1) :
        if i%2 == 0 :
            x.append(float(tab[i]))
        else:
            y.append(float(tab[i]))
    line = file.readline()
file.close()

plt.plot(x, y,'go')

for i in range(len(x)-1):
    circle2 = plt.Circle((x[i], y[i]), 85, color='blue',fill=False)
    
    fig = plt.gcf()
    ax = fig.gca()
    ax.add_patch(circle2)


#Affiche les cibles
x2 = []
y2 = []
line = file2.readline()
while line:
    tab = line.split(';')
    tab[1] = tab[1].rstrip('\n')
    x2.append(float(tab[0]))
    y2.append(float(tab[1]))
    line = file2.readline()
file2.close()

plt.plot(x2, y2,'ro')
plt.title('Nuage de points avec Matplotlib')
plt.xlabel('x')
plt.ylabel('y')
plt.show()