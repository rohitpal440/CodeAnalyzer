load features1.dat
size(features1)
sigma = (features1 * features1')/length(features1)
size(sigma)
[U,S,V] = svd (sigma);
size (U)
size (S)
size (V)
Ureduce = U(:,1:2);
size (Ureduce)
Ureduce
k = input('Enter the value of k');
cluster = Kmeans (Ureduce,k)
X = Ureduce(:,1);
size (X)
X
Y = Ureduce(:,2);
size(Y);
Y
Xcluster1 = cluster(:,1);
Ycluster1 = cluster(:,2);
size(Xcluster1);
size(Ycluster1);
[ Xcluster1, Ycluster1 ]
plot(X,Y,'.',Xcluster1,Ycluster1,'or');
pause(1000);
