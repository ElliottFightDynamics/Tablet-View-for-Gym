package com.efd.striketectablet.DTO;

public class SyncResponseDTO {
    String id;
    String serverID;

    public SyncResponseDTO(String id, String serverID) {
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
        return "SyncResponseDTO [id=" + id + ", serverID=" + serverID + "]";
    }

}
