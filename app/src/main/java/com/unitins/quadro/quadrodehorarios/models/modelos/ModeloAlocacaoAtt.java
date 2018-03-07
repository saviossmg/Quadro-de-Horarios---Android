package com.unitins.quadro.quadrodehorarios.models.modelos;

import com.unitins.quadro.quadrodehorarios.models.AlocacaoSala;
import com.unitins.quadro.quadrodehorarios.models.Oferta;

/**
 * Created by savio on 21/02/2018.
 */

public class ModeloAlocacaoAtt {

    private boolean status;
    private String mensagem;
    private AlocacaoSala aloc;
    private Oferta ofer;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public AlocacaoSala getAloc() {
        return aloc;
    }

    public void setAloc(AlocacaoSala aloc) {
        this.aloc = aloc;
    }

    public Oferta getOfer() {
        return ofer;
    }

    public void setOfer(Oferta ofer) {
        this.ofer = ofer;
    }
}
