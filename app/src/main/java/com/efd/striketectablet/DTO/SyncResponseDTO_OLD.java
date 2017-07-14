package com.efd.striketectablet.DTO;

public class SyncResponseDTO_OLD {
    String id;
    String serverID;

    public SyncResponseDTO_OLD(String id, String serverID) {
        super();
        this.id = id;
        this.serverID = serverID;
    }

    public String getId() {
        return id;
    }

    public String getServerID() {
        return serverID;
    }

    @Override
    public String toString() {
        return "SyncResponseDTO_OLD [id=" + id + ", serverID=" + serverID + "]";
    }

}
