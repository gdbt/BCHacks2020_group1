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
        id.append(row[0])
        xID.append(row[1])
        yID.append(row[2])

    plot.scatter(xID,yID,s=np.pi*3,c=(0,0,0),alpha=0.5)
    plot.title('Blueprint')
    plot.show()
