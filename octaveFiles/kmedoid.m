function [cid med l ]=kmedoid(x,k,kdist)

% K-medoid code is based on
%Park,H.S, Jun,C.H, (2009). “A Simple and Fast Algorithm for K-Medoid
%Clustering”, Expert System With Application 36. p 3336-3341.

% Input:
% x=data;
% k=number of group
% kdist=kind of distance ,1- Euclidean and 2-Mahalanobis

%Output
%cid=index member group
%med=medoid/center
%l=number member in each group

n=size(x,1);
if nargin < 2
k=n;
kdist=2;
end
if nargin < 3
kdist=2;
end
%d is distance each datum i and datum j, i=1,2,..n, j=1,2,..n
d=distance(x,kdist);

%Medoid Initation
[med index]=initmed(x,d,k);

iter = 1;
tol=0.0001;%Tolerance
err=1;
sdist=1000;

while (err>tol)
sdistold=sdist;
[sdist cid ]=member(d,index);
iter = iter + 1;
err=abs(sdistold-sdist);
[med index l]=updatemed(x,d,cid,index,k);
end

function d=distance(x,jenis)
n=size(x,1);
d2=zeros(n,n);
o=ones(n,1);

%d adalah distance each data -i and data j, i=1,2,..n, j=1,2,..n
if jenis==1%distance Euclidean

for i=1:n-1
j=(i+1):n;
d2(i,j)=sum(((x(i*o(1:length(j)),:)-x(j,:)).^2),2);
d2(j,i)=d2(i,j);
end
elseif jenis==2%distance mahalanobis
V=cov(x);%covarian data

for i=1:n-1
j=(i+1):n;
diff=(x(i*o(1:length(j)),:)-x(j,:));
d2(i,j)=sum(((diff/V).*diff),2);
d2(j,i)=d2(i,j);
end
else
error(‘stats:kmedoid:MissingInputArg’,‘pilihan distance 1 (euclidean) atau 2 (Mahalanobis)’);
end
d=sqrt(d2);
function [med index]=initmed(x,d,k)
n=size(x,1);
D=sum(d);
va=zeros(n,n);
for iv=1:n
va(:,iv)=d(:,iv)/D(iv);
end
v=sum(va,2);
[v2,index] = sort(v);
index=index(1:k,:);
med=x(index,:);

function [med index l]=updatemed(x,d,cid,index,k)
[n p]=size(x);

l=zeros(k,1);
med=zeros(k,p);

for ik = 1:k
% finding all group members.
ind = find(cid==ik);
%counting member group;
l(ik) = length(ind);
if l(ik)>0
% finding medoid.
sdist =sum(d(ind,ind));
xa=x(ind,:);
[minsdist,indms]=min(sdist);
med(ik,:)=xa(indms,:);
index(ik)=ind(indms);
end
end
function [sdist cid dist]=member(d,index)
dist = d(index,:);%distance all data to medoid all group
% Inputing data to each group
[nearest,cid] = min(dist);%nearest distance to group
sdist=sum(nearest)
