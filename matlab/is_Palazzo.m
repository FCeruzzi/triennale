function [codaPalazzi]=is_Palazzo(soglia,percentuale,codaStruttureMultilook)
    %soglia = 70;
    %percentuale = 0.4;
    import java.util.LinkedList
    codaPalazzi = LinkedList();
    D = dir(['C:\Users\Utente\Desktop\Esempio\risultatiSoloStrutture', '\*.tiff']);
    num = length(D(not([D.isdir])));
    for c = 1:num
        PossibilePalazzo = codaStruttureMultilook.get(c);
        %I = imread(strcat(strcat('C:\Users\Utente\Desktop\Esempio\risultatiSoloStrutture\francobolloML',num2str(c)),'.tiff'));
        numPuntiScuri = 0;
        numPuntiTotali = 0;
        for l = 1:size(PossibilePalazzo,1)
            for k = 1:size(PossibilePalazzo,2)
                numPuntiTotali = numPuntiTotali + 1;
                if PossibilePalazzo(l,k) < soglia
                    numPuntiScuri = numPuntiScuri +1;
                end
            end
        end
        risultato = (numPuntiScuri)/numPuntiTotali;
        if risultato > percentuale
            %da modificare con imWrite in apposita cartella
            %imwrite(I, strcat(strcat('C:\Users\Utente\Desktop\Esempio\risultatiFiltrati\francobolloML',num2str(c)),'.tiff'));
            %delete(strcat(strcat('C:\Users\Utente\Desktop\Esempio\risultatiSoloStrutture\francobolloML',num2str(c)),'.tiff'));
            codaPalazzi.add(PossibilePalazzo);
        end
end
end
