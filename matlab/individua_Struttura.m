function [codaStrutture] = individua_Struttura(Sobel,coox,cooy,dimBox,dimStruttura,dimArea)
    %cd 'C:\Users\Utente\Desktop\Esempio\risultati';
    %delete *.tiff
    %Sobel = imread('C:\Users\Utente\Desktop\Esempio\Sobel.tiff');
    %coox = 1600;
    %cooy = 1974;
    %dimBox = 3;
    %dimStruttura = 50;
    %dimArea = 550;
    SCUT = Sobel(coox-round(dimArea/2):coox+round(dimArea/2),cooy-round(dimArea/2):cooy+round(dimArea/2));
    imwrite(SCUT, 'C:\Users\Utente\Desktop\Esempio\SCUT.tiff');
    import java.util.LinkedList
    coda = LinkedList();
    numerostruttura = 0;
    codaStrutture = LinkedList();
    for m = dimBox*2:dimArea-(dimBox*2)
        for n = dimBox*2:dimArea-(dimBox*2)
            if(SCUT(m,n) == 1)
                spi = m;
                spj = n;
                Struttura = zeros(dimArea,dimArea);
                continua = true;
                while continua 
                    if spi > dimBox*2 && spi < dimArea-(dimBox*2) && spj >dimBox*2 && spj < dimArea-(dimBox*2) 
                        Box = SCUT(spi-dimBox:spi+dimBox,spj-dimBox:spj+dimBox);
                        for coox = 1:(2*dimBox+1)
                            for cooy = 1:(2*dimBox+1)
                                if Box(coox,cooy) == 1
                                        coda.add([spi+coox-(dimBox+1),spj+cooy-(dimBox+1)]);
                                    SCUT(spi+coox-(dimBox+1),spj+cooy-(dimBox+1)) = 0;
                                    Struttura(spi+coox-(dimBox+1),spj+cooy-(dimBox+1)) = 1;
                                end 
                            end
                        end
                    end
                    if coda.size() == 0
                        continua = false;
                    else
                        coordinata = coda.remove();
                        spi = coordinata(1);
                        spj = coordinata(2);
                    end
                end
                if sum(sum(Struttura)) > dimStruttura
                    codaStrutture.add(Struttura);
                    numerostruttura = numerostruttura +1;
                    %imwrite(Struttura, strcat(strcat('C:\Users\Utente\Desktop\Esempio\risultati\',num2str(numerostruttura)),'.tiff'));
                end
            end
        end
    end       
end
