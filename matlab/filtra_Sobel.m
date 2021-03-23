function [Sobel] = filtra_Sobel(ImmagineMultilook)
%I = imread('ImmagineMultilookTemporale.tiff');
Sobel = edge(ImmagineMultilook,'Sobel',0.20,'both');%0.15
%imwrite(S,'S.tiff');
end
