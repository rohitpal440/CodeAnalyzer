load features.dat
size(features)
featuresT = feaures * features';
featruesT
sigma = featuresT/length(features)
size(sigma)
[U,S,V] = svd (sigma);
size (U)
size (S)
size (v)
Ureduce = U(:,1:2);
size (Ureduce)
Ureduce
cluster = Kmeans (Ureduce,4)
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
