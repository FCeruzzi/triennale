function [codaAree]= calcolo_Area(codaStrutturaMultilook,soglia,dimBox,numeroPuntiMax)
    %Prendo tutti quelli maggiori della soglia
    %soglia = 150;
    %dimBox = 2;%2
    %numeroPuntiMax = 5;%2- valori migliori 5,6(cancella anche alcune cose buone)
    import java.util.LinkedList
    codaAree = LinkedList();
    dimStruttureReali = 1.5*1.5;    
    for k = 1:codaStrutturaMultilook.size()
        StrutturaMultilook = codaStrutturaMutlilook.get();
        PrimaMaschera = zeros(size(StrutturaMultilook,1),size(StrutturaMultilook,2));
        Area = zeros(size(StrutturaMultilook,1),size(StrutturaMultilook,2));
        coda = LinkedList();
        for i = 1:size(StrutturaMultilook,1)
            for j = 1:size(StrutturaMultilook,2)
                if StrutturaMultilook(i,j) >= soglia
                    Area(i,j) = 1;
                end
            end
        end
        AreaIniziale = Area;
        imwrite(AreaIniziale, 'areaIniziale.tiff');
        for i = dimBox+1:size(StrutturaMultilook,1)-dimBox
            for j = dimBox+1:size(StrutturaMultilook,2)-dimBox
                if Area(i,j)~=0
                    Box = Area(i-dimBox:i+dimBox,j-dimBox:j+dimBox);
                    for m = 1:(2*dimBox+1)
                        for n = 1:(2*dimBox+1)
                            if Box(m,n) ==1 && m ~= 2 && n ~= 2
                                Area(m+i-(dimBox+1),n+j-(dimBox+1)) = 1;
                            end 
                        end
                    end
                end
            end
        end
        %da qui togliere per far funzionare la parte commentata
        for i = dimBox+1:size(StrutturaMultilook,1)-dimBox
            for j = dimBox+1:size(StrutturaMultilook,2)-dimBox
                if PrimaMaschera(i,j)~=0
                    Box = Area(i-dimBox:i+dimBox,j-dimBox:j+dimBox);
                    numeroPunti = 0;
                    for m = 1:(2*dimBox+1)
                        for n = 1:(2*dimBox+1)
                            if Box(m,n) ==1 && m ~= 2 && n ~= 2
                                numeroPunti = numeroPunti + 1;
                            end 
                        end
                    end
                    if numeroPunti <= numeroPuntiMax
                        coda.add([i,j]);
                    end
                end
            end
        end
        while ~coda.isEmpty()
            coordinata = coda.remove();
            Area(coordinata(1),coordinata(2)) = 0;
        end
        imwrite(Area, 'area.tiff');
        BW = bwmorph(Edgestrutt,'thicken', 5);
        Area = zeros(size(StrutturaMultilook,1),size(StrutturaMultilook,2));
        for m = dimBox+1:size(StrutturaMultilook,1)-dimBox
            for n = dimBox+1:size(StrutturaMultilook,2)-dimBox
                if(PrimaMaschera(m,n)~=0 ||BW(m,n) ~=0)
                    Area(m,n) = 1;
                end
            end
        end
        Area = bwmorph(Area,'bridge',Inf);
        Area = bwmorph(Area,'close',Inf);
        Area = Area.*AreaIniziale;
        imwrite(Area, 'areaFinale.tiff');
        area = sum(sum(Area))*dimStruttureReali;
        codaAree.add(area);
    end
end