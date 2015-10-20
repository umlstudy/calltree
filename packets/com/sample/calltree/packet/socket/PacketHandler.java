package com.sample.calltree.packet.socket;

import com.sample.calltree.packet.Packet;

public interface PacketHandler {
	void handlePacket(Packet packet);
}
