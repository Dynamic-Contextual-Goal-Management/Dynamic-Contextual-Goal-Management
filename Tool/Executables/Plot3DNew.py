import numpy as np
import matplotlib.pyplot as plt
import math
from mpl_toolkits import mplot3d

pi = math.pi


def nCr(n, r):
    return (fact(n) / (fact(r)
                       * fact(n - r)))


def fact(n):
    res = 1

    for i in range(2, n + 1):
        res = res * i

    return res


nCr_list = []
# Driver code
n = 10
r = 3
for i in range(0, n + 1):
    print("i is = ", i)
    nCr_list.append(int(nCr(n, i)))

print(nCr_list)

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')

ax.set_zlabel('No. of Context')
ax.set_yticklabels([])
ax.set_xticklabels([])


def PointsInCircum(r, n=25):
    return [(math.cos(2 * pi / n * x) * r, math.sin(2 * pi / n * x) * r) for x in range(0, n + 1)]


coordinates = []
z = 0
k = 0
while k <= n:
    # r = random.randrange(1, 5)
    # n = random.randrange(15, 20)
    r = nCr(n, k)
    n1 = nCr(n, k)
    # print(int(r))
    xpoints = []
    ypoints = []
    coordinate = []
    PointsList = PointsInCircum(int(r), int(n1))
    # PointsList.pop(0)
    count = 0
    for i in PointsList:
        x = i[0]
        y = i[1]
        coordinate = [x, y, z]
        coordinates.append(coordinate)
        xpoints.append(x)
        ypoints.append(y)
        # print("x = "+str(x)+" y = "+str(y)+" z = "+str(z))
        ax.scatter(x, y, z, c="black", s=1)
        # ax.text(x, y, z,  '%s' % (str(count)), size=10, zorder=1, color='k')
        count += 1

    ax.plot(xpoints, ypoints, z, '--')
    z = z + 1
    k = k + 1

# taking the new colored node location
color_cordinate = []

"""
#  Start of 1st portion
# if the context array look like [1,2,3] // [layerNo,layerNo,....]
context_list = [1,2]

# here the color and add colored node
def change_node(z_index):
    for i in coordinates:
        if (i[2] == int(z_index) ):
            print(i)
            ax.scatter(i[0], i[1], i[2])
            color_cordinate.append(i)
            break

# here the context loop
for i in context_list:
    change_node(i)

print("color_cordinate is ",color_cordinate)
#  end of 1st portion
"""

#  Start of 2nd portion
# if the context_array look like [[layerNo,nodeNo],....]

context_list = [[2, 2], [3, 4], [2, 22], [2, 30], [5, 79], [4, 155], [6, 85], [7, 69], [7, 87], [6, 5], [8, 4], [8, 6],
                [7, 52], [7, 115], [6, 68]]


# ,[6,55],[5,85],[5,100],[8,20],[4,25],[9,4],[9,6],[3,78],[3,98],[2,39],[4,195],[5,224],[6,182],[6,145],[4,205],[7,100],[5,240],[3,10],[5,140],[5,180],[5,220],[1,5],[1,8],[6,49],[4,147]]
# 7, 18, 9, 20, 21
def change_node(index):
    count_i = 0
    for i in coordinates:
        if (i[2] == int(index[0])):
            # print("index of zero: "+str(index[0]))
            # print("index of one: " + str(index[1]))
            if (count_i == index[1]):
                # print("Matched index of 1: " + str(index[1]))
                ax.scatter(i[0], i[1], i[2], s=15)
                color_cordinate.append(i)
            count_i += 1


for i in context_list:
    change_node(i)

# print("color_cordinate is ",color_cordinate)
# print(context_list)
# print(context_list[4],color_cordinate[4])
subsetList = []
supersetList = []
x1 = color_cordinate[3][0]
y1 = color_cordinate[3][1]
z1 = color_cordinate[3][2]
x2 = color_cordinate[7][0]
y2 = color_cordinate[7][1]
z2 = color_cordinate[7][2]
supersetList.append([x2,y2,z2])
subsetList.append([x1,y1,z1])
ax.plot([x1,x2],[y1,y2],[z1, z2], c='red')

x1 = color_cordinate[5][0]
y1 = color_cordinate[5][1]
z1 = color_cordinate[5][2]
x2 = color_cordinate[9][0]
y2 = color_cordinate[9][1]
z2 = color_cordinate[9][2]
supersetList.append([x2,y2,z2])
subsetList.append([x1,y1,z1])
ax.plot([x1,x2],[y1,y2],[z1, z2], c='red')

x1 = color_cordinate[4][0]
y1 = color_cordinate[4][1]
z1 = color_cordinate[4][2]
x2 = color_cordinate[7][0]
y2 = color_cordinate[7][1]
z2 = color_cordinate[7][2]
if z1>z2:
    subsetList.append([x2,y2,z2])
    supersetList.append([x1,y1,z1])
elif z2>z1:
    subsetList.append([x1,y1,z1])
    supersetList.append([x2,y2,z2])

else:
    print("Fail at: "+str([x1,y1,z1])+" and "+str([x2,y2,z2]))
ax.plot([x1,x2],[y1,y2],[z1, z2], c='red')

x0 = coordinates[0][0]
y0 = coordinates[0][1]
z0 = coordinates[0][2]

#last index is -1
xn = coordinates[-1][0]
yn = coordinates[-1][1]
zn = coordinates[-1][2]



for i in color_cordinate:
    xc = i[0]
    yc = i[1]
    zc = i[2]
    ax.plot([x0,xc],[y0,yc],[z0, zc], c='grey')




res = [i for i in color_cordinate if i not in subsetList]
#res = [i for i in res if i not in supersetList]
for i in res:
    xc = i[0]
    yc = i[1]
    zc = i[2]
    ax.plot([x0,xc],[y0,yc],[z0, zc], c='grey')
    ax.plot([xn,xc],[yn,yc],[zn, zc],'--', c='grey')
#  End of 2nd portion
print(coordinates[-1])
plt.show()
