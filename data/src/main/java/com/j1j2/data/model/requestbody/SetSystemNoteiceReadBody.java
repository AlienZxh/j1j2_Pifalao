package com.j1j2.data.model.requestbody;

import java.util.List;

/**
 * Created by alienzxh on 16-3-10.
 */
public class SetSystemNoteiceReadBody {

    private List<Integer> RecordIdList;

    public List<Integer> getRecordIdList() {
        return RecordIdList;
    }

    public void setRecordIdList(List<Integer> recordIdList) {
        RecordIdList = recordIdList;
    }

    @Override
    public String toString() {
        return "SetSystemNoteiceReadBody{" +
                "RecordIdList=" + RecordIdList +
                '}';
    }
}
