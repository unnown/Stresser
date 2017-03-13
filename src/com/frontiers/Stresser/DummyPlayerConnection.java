package com.frontiers.Stresser;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInEnchantItem;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_8_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayInTransaction;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.WorldServer;

public class DummyPlayerConnection extends PlayerConnection {
    @SuppressWarnings("unused")
	private boolean disconnected = false;

    public DummyPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
    }

    @Override
    public CraftPlayer getPlayer() {
        return (this.player == null) ? null : this.player.getBukkitEntity();
    }

    public NetworkManager b() {
        return this.networkManager;
    }

    public void disconnect(java.lang.String s) {    	
        WorldServer worldserver = player.getWorld().getWorld().getHandle();

        worldserver.kill(player);
        worldserver.getPlayerChunkMap().removePlayer(player);
        ((CraftServer) Bukkit.getServer()).getHandle().players.remove(player);
        this.disconnected = true;
    }

    public void a(PacketPlayInSteerVehicle packetplayinsteervehicle) {
    }

    public void a(PacketPlayInFlying packetplayinflying) {
    }

    public void a(double d0, double d1, double d2, float f, float f1) {
    }

    public void teleport(Location dest) {
    }

    public void a(PacketPlayInBlockDig packetplayinblockdig) {
    }

    public void a(PacketPlayInBlockPlace packetplayinblockplace) {
    }

    public void a(IChatBaseComponent ichatbasecomponent) {
    }

    public void sendPacket(Packet packet) {
    }

    public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot) {
    }

    public void a(PacketPlayInChat packetplayinchat) {
    }

    public void chat(String s, boolean async) {
    }

    public void a(PacketPlayInArmAnimation packetplayinarmanimation) {
    }

    public void a(PacketPlayInEntityAction packetplayinentityaction) {
    }

    public void a(PacketPlayInUseEntity packetplayinuseentity) {
    }

    public void a(PacketPlayInClientCommand packetplayinclientcommand) {
    }

    public void a(PacketPlayInCloseWindow packetplayinclosewindow) {
    }

    public void a(PacketPlayInWindowClick packetplayinwindowclick) {
    }

    public void a(PacketPlayInEnchantItem packetplayinenchantitem) {
    }

    public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot) {
    }

    public void a(PacketPlayInTransaction packetplayintransaction) {
    }

    public void a(PacketPlayInUpdateSign packetplayinupdatesign) {
    }

    public void a(PacketPlayInKeepAlive packetplayinkeepalive) {
    }

    public void a(PacketPlayInAbilities packetplayinabilities) {
    }

    public void a(PacketPlayInTabComplete packetplayintabcomplete) {
    }

    public void a(PacketPlayInSettings packetplayinsettings) {
    }

    public void a(PacketPlayInCustomPayload packetplayincustompayload) {
    }

    public void a(EnumProtocol enumprotocol, EnumProtocol enumprotocol1) {
    }
}
