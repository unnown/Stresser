package com.frontiers.Stresser;

import javax.crypto.SecretKey;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;

import java.net.SocketAddress;

public class DummyNetworkManager extends NetworkManager {
    private IChatBaseComponent ichatbasecomponent;

    public DummyNetworkManager() {
    	super(EnumProtocolDirection.CLIENTBOUND);
    }

    public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception {
    }

    public void a(EnumProtocol enumprotocol) {
    }

    public void channelInactive(ChannelHandlerContext channelhandlercontext) {
    }

    public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
    }

    protected void a(ChannelHandlerContext channelhandlercontext, Packet packet) {
    }

    public void a(PacketListener packetlistener) {
        super.a(packetlistener);
    }

    public void handle(Packet packet, GenericFutureListener... agenericfuturelistener) {
    }

    private void b(Packet packet, GenericFutureListener[] agenericfuturelistener) {
    }

    public void a() {
    }

    public SocketAddress getSocketAddress() {
        return new SocketAddress() {
        };
    }

    public void a(IChatBaseComponent ichatbasecomponent) {
        this.ichatbasecomponent = ichatbasecomponent;
    }

    public boolean c() {
        return false;
    }

    public void a(SecretKey secretkey) {
    }

    public boolean d() {
        return true;
    }

    public PacketListener getPacketListener() {
        return super.getPacketListener();
    }

    public IChatBaseComponent f() {
        return this.ichatbasecomponent;
    }
    
    static Channel a(NetworkManager networkmanager) {
        return null;
    }
}
