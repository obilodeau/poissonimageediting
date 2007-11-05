function [imNew] = poissonSolverSeamlessCloning1(imSrc, imDest, imMask, offset)
%
% rectangular solver
% 
% parameters
% imSrc - source image
% imDest - destination image
% imMask - a black and white mask to indicate the irregular boundary
%
% posOffset - offset of corresponding pixel from the source to the
% destination
%

% --------------------------------------------
% global variables
% --------------------------------------------

laplacian = [0 1 0; 1 -4 1; 0 1 0];

% height and width of both the source image and the destination image
[heightSrc widthSrc] = size(imSrc);
[heightDest widthDest] = size(imDest);
[heightMask widthMask] = size(imMask);

% check if the mask is too big
if heightSrc < heightMask | widthSrc < widthMask | heightDest < heightMask | widthDest < widthMask
    fprintf('Error - the mask is too big\n');    
end

% the offset between the source and the destination
xoff = offset(1);
yoff = offset(2);

% --------------------------------------------
% calculate the number of pixels that are 0
% for sparse matrix allocation
% --------------------------------------------
n = size(find(imMask), 1);

%---------------------------------------------
% sparse matrix allocation
%---------------------------------------------
fprintf('Matrix dimension = %d x %d\n', n, n);
M = spalloc(n, n, 5*n);

% also the boundary condition
b = zeros(1, n);

% temperary matrix index holder
% need to point the pixel position in the image to
% the row index of the solution vector to
imIndex = zeros(heightDest, widthDest);


fprintf('Building index\n');
tic

count = 0;
% now fill in the 
for y = 1:heightDest
    for x = 1:widthDest
        if imMask(y+yoff, x+xoff) ~= 0
            count = count + 1;            
            imIndex(y, x) = count;
        end
    end
end

toc
        
if count ~= n
    fprintf('Error - wrong matrix size\n');
end

        
        
%---------------------------------------------
% construct the matrix here
%---------------------------------------------

tic

% construct the laplacian image.
imLaplacian = conv2(imSrc, -laplacian, 'same');

% matrix row count
count = 0; % count is the row index
for y = 1:heightSrc
    for x = 1:widthSrc

        % if the mask is not zero, then add to the matrix
        if imMask(y, x) ~= 0

            % increase the counter
            count = count + 1;   
            
            % the corresponding position in the destination image
            yd = y - yoff;
            xd = x - xoff; 
             
            %------------------------------------------------------
            % if Neighbourhood(p) is in the interia of the region
            %------------------------------------------------------
            
            
            % gathering the coefficient for the matrix
            %------------------------------------------------------
            % if on the top
            if imMask(y-1, x) ~= 0
                % this pixel is already used
                % get the diagonal position of the pixel
                colIndex = imIndex(yd-1, xd);
                M(count, colIndex) = -1;
            else % at the top boundary
                b(count) = b(count) + imDest(yd-1, xd);
            end
            
            % if on the left
            if imMask(y, x-1) ~= 0
                colIndex = imIndex(yd, xd-1);
                M(count, colIndex) = -1;
            else % at the left boundary
                b(count) = b(count) + imDest(yd, xd-1);
            end            
            
            %------------------------------------------------------
            % now the harder case, since this is not allocated
            %------------------------------------------------------ 
            % if on the bottom            
            if imMask(y+1, x) ~= 0
                colIndex = imIndex(yd+1, xd);
                M(count, colIndex) = -1;
            else    % at the bottom boundary
                b(count) = b(count) + imDest(yd+1, xd);
            end
            
            % if on the right side
            if imMask(y, x+1) ~= 0
                colIndex = imIndex(yd, xd+1);
                M(count, colIndex) = -1;
            else    % at the right boundary
                b(count) = b(count) + imDest(yd, xd+1);
            end       
            
            M(count, count) = 4;
            
            % construct the guidance field	
            v = imLaplacian(y, x);
	
            b(count) = b(count)+v;

        end
    end
end

if count ~= n
    fprintf('Error - wrong matrix size\n');
end


toc

%---------------------------------------------
% solve for the sparse matrix
%---------------------------------------------
tic
% use bi-conjugate gradient method to solve the matrix
x = bicg(M, b', [], 300);
toc

%---------------------------------------------
% now fill in the solved values
%---------------------------------------------
imNew = imDest;

fprintf('\nRetriving result, filling destination image\n');
tic
% now fill in the 
for y1 = 1:heightDest
    for x1 = 1:widthDest
        if imMask(y1+yoff, x1+xoff) ~= 0
            index = imIndex(y1, x1);
            imNew(y1, x1) = x(index);
        end
    end
end

toc