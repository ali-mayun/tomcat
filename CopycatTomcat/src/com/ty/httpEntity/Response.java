package com.ty.httpEntity;

import java.io.OutputStream;

public class Response {
	private OutputStream output;
    
    public OutputStream getOutput() {
        return output;
    }

    public void setOutput(OutputStream output) {
        this.output = output;
    }

    public Response(OutputStream output) {
        this.output = output;
    }
}
