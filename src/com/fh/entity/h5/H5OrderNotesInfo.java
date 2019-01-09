package com.fh.entity.h5;

import java.io.Serializable;

public class H5OrderNotesInfo implements Serializable {
	private static final long serialVersionUID = 1L;
    /** 备注 */
    private String notes; // 备注
    /** 备注人 */
    private String addusername; // 备注人

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAddusername() {
        return addusername;
    }

    public void setAddusername(String addusername) {
        this.addusername = addusername;
    }
}
