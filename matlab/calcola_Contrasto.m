function [risultato,cr] = calcola_Contrasto(ImmagineMultilook, codaStruttureEdge, coox,cooy,dimBox,cornice)
    %parte VI - algoritmo 2
    %calcola cr
    %ImmagineMultilook = imread('C:\Users\Utente\Desktop\Esempio\ImmagineMultilookTemporale.tiff');
    %Struttura = imread('C:\Users\Utente\Desktop\Esempio\risultati\15.tiff');
    %coox = 1597;
    %cooy = 1975;
    %dimBox = 550; 
    %cornice = 5;
    for c=1:codaStruttureEdge.size()
        Struttura = codaStruttura.get(c);
        sumxin = 0;
        sumxout = 0;
        [righe,colonne]= find(Struttura);
        mini = min(righe);
        minj = min(colonne);
        maxi = max(righe);
        maxj = max(colonne);
        SobelStrutt = ImmagineMultilook(coox-round(dimBox/2)+mini:coox-round(dimBox/2)+maxi,cooy-round(dimBox/2)+minj:cooy-round(dimBox/2)+maxj);
        mini = min(righe)-cornice;
        minj = min(colonne)-cornice;
        maxi = max(righe)+cornice;
        maxj = max(colonne)+cornice;
        SobelStruttCornice = ImmagineMultilook(coox-round(dimBox/2)+mini:coox-round(dimBox/2)+maxi,cooy-round(dimBox/2)+minj:cooy-round(dimBox/2)+maxj);
        m_in = size(SobelStrutt,1)*size(SobelStrutt,2);
        m_out = size(SobelStruttCornice,1)*size(SobelStruttCornice,2);
        NormSobelStrutt = mat2gray(SobelStrutt);
        NormSobelStruttCornice = mat2gray(SobelStruttCornice);
        for coox = 1: size(NormSobelStrutt,1) 
            for cooy = 1: size(NormSobelStrutt,2) 
                xin = NormSobelStrutt(coox,cooy);
                sumxin = sumxin + xin;
            end
        end
        for coox = 1: size(NormSobelStruttCornice,1) 
            for cooy = 1: size(NormSobelStruttCornice,2) 
                xout = NormSobelStruttCornice(coox,cooy);
                sumxout = sumxout + (1-xout);
            end
        end
        cr = ((1/m_in)*sumxin)*((1/m_out)*sumxout);
        risultato = false;
        if cr > (20/100)
            risultato = true;
        end
    end
end
