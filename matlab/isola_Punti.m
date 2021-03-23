function [codaAreeMigliorate] = isola_Punti(codaAree,dimBox,numeroPuntiMax)
import java.util.LinkedList
codaAreeMigliorate = LinkedList();
%parte VI-algoritmo 1
    for k = 1:codaAree.size()
        AreeMigliorate = codaAree.get();
        %dimBox = 5;%4
        %numeroPuntiMax = 11;%6
        coda = LinkedList();
        for a = dimBox+1:size(AreeMigliorate,1)-dimBox
            for b = dimBox+1:size(AreeMigliorate,2)-dimBox
                if AreeMigliorate(a,b)~= 0
                    Box = AreeMigliorate(a-dimBox:a+dimBox,b-dimBox:b+dimBox);
                    numeroPunti = 0;
                    for m = 1:(2*dimBox+1)
                        for n = 1:(2*dimBox+1)
                            if Box(m,n) ~= 0 && m ~= round(dimBox/2)+1 && n ~= round(dimBox/2)+1
                                numeroPunti = numeroPunti + 1;
                            end 
                        end
                    end
                    if numeroPunti <= numeroPuntiMax
                        coda.add([a,b]);
                    end
                end
            end
        end
        %while ~coda.isEmpty()
        for k = 1:coda.size()
            coordinata = coda.remove();
            AreeMigliorate(coordinata(1),coordinata(2)) = 0;   
        end
        AreeMigliorate = bwmorph(AreeMigliorate,'clean', Inf);
        imwrite(AreeMigliorate, 'areaIsolaPunti.tiff');
        codaAreeMigliorate.add(AreeMigliorate);
    end
end