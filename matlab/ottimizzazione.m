function [codaStruttureMultilook,codaStruttureEdge]  = ottimizzazione(ImmagineMultilook, coox,cooy,dimArea, codaStrutture)
    %IV parte - ex Taglia, serve a "francobollare"
    %I = imread('C:\Users\Utente\Desktop\Esempio\ImmagineMultilookTemporale.tiff');
    import java.util.LinkedList
    codaStruttureMultilook = LinkedList();
    codaStruttureEdge = LinkedList();
    for k = 1:codaStrutture.size()
        Struttura = codaStrutture.get();
        %Struttura = imread('C:\Users\Utente\Desktop\Esempio\risultati\15.tiff');
        %coox = 1597;
        %cooy = 1975;
        %dimArea = 550; 
        [righe,colonne]= find(Struttura);
        mini = min(righe);
        minj = min(colonne);
        maxi = max(righe);
        maxj = max(colonne);
        StrutturaMultilook = ImmagineMultilook(coox-round(dimArea/2)+mini:coox-round(dimArea/2)+maxi,cooy-round(dimArea/2)+minj:cooy-round(dimArea/2)+maxj);
        StrutturaEdge = Struttura(mini:maxi,minj:maxj);
        codaStruttureMultilook.add(StrutturaMultilook);
        codaStruttureEdge.add(StrutturaEdge);
        %imwrite(Edgestrutt, 'C:\Users\Utente\Desktop\Esempio\francobolloEdge.tiff');
        %imwrite(Sobelstrutt, 'C:\Users\Utente\Desktop\Esempio\francobolloML.tiff');
    end
end