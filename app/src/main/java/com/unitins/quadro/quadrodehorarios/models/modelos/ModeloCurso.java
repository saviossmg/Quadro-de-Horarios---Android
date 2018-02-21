package com.unitins.quadro.quadrodehorarios.models.modelos;

import com.unitins.quadro.quadrodehorarios.models.Curso;

import java.util.List;

/**
 * Created by savio on 10/02/2018.
 */

public class ModeloCurso {

    private boolean status;
    private String msg;
    private List<Curso> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Curso> getData() {
        return data;
    }

    public void setData(List<Curso> data) {
        this.data = data;
    }

}
