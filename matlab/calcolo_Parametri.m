function [plot_Hough,lunghezzaMediaReale,probabilitaErrore] = calcolo_Parametri(ImmagineMultilook, Sobel,dimensioneRealePixel)
close all;
[H,theta,rho] = hough(Sobel);%francobollo di edge
P = houghpeaks(H,100,'threshold',ceil(0.3*max(H(:))));%mappa dei picchi, %H,100
lines = houghlines(Sobel,theta,rho,P,'FillGap',3,'MinLength',10);%BW francobollo di edge%MinLength,5,30
figure;
imshow(ImmagineMultilook); 
hold on;
max_len = 0;
lunghezzaMedia = 0;
numeroLati = 0;
MatriceLinee = zeros(550,550); 
for k = 1:length(lines)
   xy = [lines(k).point1; lines(k).point2];
   plot(xy(:,1),xy(:,2),'LineWidth',2,'Color','green');
   plot(xy(1,1),xy(1,2),'x','LineWidth',2,'Color','yellow');
   plot(xy(2,1),xy(2,2),'x','LineWidth',2,'Color','red');
   len = norm(lines(k).point1 - lines(k).point2);%lunghezza del segmento visualizzato, da usare per perimetro
   lunghezzaMedia = lunghezzaMedia + len;
   numeroLati = numeroLati + 1;
   if ( len > max_len)%segmento in rosso
      max_len = len;
      xy_long = xy;
   end
   x1 = lines(k).point1(1);
   y1 = lines(k).point1(2);
   x2 = lines(k).point2(1);
   y2 = lines(k).point2(2);
   xinizio = x1;
   xfine = x2;
   %yInizio = y1;
   if x1 > x2
       xinizio = x2; 
       xfine = x1;
   end
   %if y1 > y2
   %    y = y2;
   %end
   for x = xinizio : xfine-1
        y = round(y1 + ((y2-y1)/(x2-x1))*(x-x1));
        MatriceLinee(x,y) = 255;
   end
end
plot_Hough = plot(xy_long(:,1),xy_long(:,2),'LineWidth',2,'Color','red');
%saveas(plot_Hough, 'plotSobelAreaIsolaPunti.png','png');
%saveas(plot_Hough, 'plotSobelAreaFinale.png','png');
lunghezzaMedia = lunghezzaMedia/numeroLati;
lunghezzaMediaReale = lunghezzaMedia * dimensioneRealePixel;
probabilitaErrore = (sum(sum(MatriceLinee))/255)/(sum(sum(Sobel))/255);
end