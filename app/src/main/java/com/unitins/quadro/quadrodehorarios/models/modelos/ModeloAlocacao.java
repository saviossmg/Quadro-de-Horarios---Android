package com.unitins.quadro.quadrodehorarios.models.modelos;

import com.unitins.quadro.quadrodehorarios.models.AlocacaoSala;
import com.unitins.quadro.quadrodehorarios.models.Oferta;

import java.util.List;

/**
 * Created by savio on 14/02/2018.
 */

public class ModeloAlocacao {

    private boolean status;
    private String mensagem;
    private List<AlocacaoSala> aloc;
    private List<Oferta> ofer;

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

    public List<AlocacaoSala> getAloc() {
        return aloc;
    }

    public void setAloc(List<AlocacaoSala> aloc) {
        this.aloc = aloc;
    }

    public List<Oferta> getOfer() {
        return ofer;
    }

    public void setOfer(List<Oferta> ofer) {
        this.ofer = ofer;
    }
}
