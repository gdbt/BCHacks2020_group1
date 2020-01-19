import numpy as np
import matplotlib.pyplot as plot
import csv

with open('dataFile.csv') as file: 
    id = []
    xID = []
    yID = []
    col = 0
    csv_reader = csv.reader(file, delimiter=',')
    for row in csv_reader:
        id.append(int(row[0]))
        xID.append(float(row[1]))
        yID.append(float(row[2]))

    plot.scatter(xID,yID,s=np.pi*3,alpha=0.5)
    plot.title('Blueprint')
    plot.show()
