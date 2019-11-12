from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import math
import numpy as np
import random

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')


pi = math.pi

def nCr(n, r):
    return (fact(n) / (fact(r)
                           * fact(n - r)))

    # Returns factorial of n
def fact(n):
    res = 1
    for i in range(2, n + 1):
        res = res * i

    return res

def PointsInCircum(r,n=25):
    return [(math.cos(2*pi/n*x)*r,math.sin(2*pi/n*x)*r) for x in range(0,n+1)]



coordinates=[]
z = 1
k = 0
while k<=5:
    #r = random.randrange(1, 5)
    #n = random.randrange(15, 20)
    r = nCr(5, k)
    n = nCr(5, k)
    print(int(r))
    xpoints=[]
    ypoints=[]
    coordinate=[]
    PointsList = PointsInCircum(int(r), int(n))
    print("for k = "+str(k)+" z = "+str(z))
    for i in PointsList:
        x = i[0]
        y = i[1]
        coordinate = [x, y, z]
        coordinates.append(coordinate)
        xpoints.append(x)
        ypoints.append(y)
        print("x = "+str(x)+" y = "+str(y))
        ax.scatter(x, y, z, c='black')

    ax.plot(xpoints, ypoints, z, '--')
    z = z + 3
    k = k + 1

    
xp = [-3.0901699437494754, 1.5450849718747373]
yp = [-9.510565162951535, 4.755282581475767]
zp = [7, 13]
ax.plot(xp, yp, zp, c='black')
#ax.scatter(-4.045084971874736, 2.9389262614623664, 4, c='r', s=50)
# ax.scatter(3.0901699437494745,  9.510565162951535, 10, c='r', s=50)
# ax.scatter(8.090169943749475, 5.877852522924732, 7, c='r', s=50)
# ax.scatter(3.0901699437494745, 9.510565162951535, 7, c='r', s=50)

#new added
ax.scatter(-3.0901699437494754, -9.510565162951535, 7, c='r', s=50)
ax.scatter(3.0901699437494745, 9.510565162951535, 10, c='r', s=50)
ax.scatter(-3.0901699437494736, 9.510565162951536, 10, c='r', s=50)
ax.scatter(1.5450849718747373, 4.755282581475767, 13, c='r', s=50)
ax.scatter(1.0, -2.4492935982947064e-16, 1) #phi

#connect points with phi

#ax.plot([1.0, -4.045084971874736],[-2.4492935982947064e-16, 2.9389262614623664],[1, 4], c='black')
ax.plot([1.0, 3.0901699437494745],[-2.4492935982947064e-16, 9.510565162951535],[1, 10], c='black')
#ax.plot([1.0, 8.090169943749475],[-2.4492935982947064e-16, 5.877852522924732],[1, 7], c='black')
#ax.plot([1.0, 3.0901699437494745],[-2.4492935982947064e-16, 9.510565162951535],[1, 7], c='black')
ax.plot([1.0, 1.5450849718747373],[-2.4492935982947064e-16, 4.755282581475767],[1, 13], c='black')
ax.plot([1.0, -3.0901699437494754],[-2.4492935982947064e-16, -9.510565162951535],[1, 7], c='black')
ax.plot([1.0, -3.0901699437494736],[-2.4492935982947064e-16, 9.510565162951536],[1, 10], c='black')
#connect ncn to points

#ax.plot([1.0, -4.045084971874736],[-2.4492935982947064e-16, 2.9389262614623664],[16, 4], '--', c='black')
ax.plot([1.0, 3.0901699437494745],[-2.4492935982947064e-16, 9.510565162951535],[16, 10], '--', c='black')
#ax.plot([1.0, 8.090169943749475],[-2.4492935982947064e-16, 5.877852522924732],[16, 7], '--', c='black')
#ax.plot([1.0, 3.0901699437494745],[-2.4492935982947064e-16, 9.510565162951535],[16, 7], '--', c='black')
ax.plot([1.0, 1.5450849718747373],[-2.4492935982947064e-16, 4.755282581475767],[16, 13], '--', c='black')
ax.plot([1.0, -3.0901699437494754],[-2.4492935982947064e-16, -9.510565162951535],[16, 7], '--', c='black')
ax.plot([1.0, -3.0901699437494736],[-2.4492935982947064e-16, 9.510565162951536],[16, 10], '--', c='black')
#ax.scatter(x, y, z, c='r', marker='o')
# ax.set_xlim(0, 20)
# ax.set_ylim(0, 20)
# ax.set_zlim(0, 6)
ax.set_xlabel('X Label')
ax.set_ylabel('Y Label')
ax.set_zlabel('Z Label')

plt.show()
