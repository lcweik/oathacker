package common;


public class DataTransfer {
	
	public byte[] m_byData = null;
	public long m_Base;
	
	public DataTransfer(byte[] Data)
	{
		m_Base = 0;
		m_byData = Data;
	}
	
	public void SetBase(long m_string_offset) {
		m_Base = m_string_offset;
	}
	
	public int DataInt(int Offset)
	{
		Offset += m_Base;
		int result = m_byData[Offset] & 0xff | ((m_byData[Offset+1] & 0xff) << 8) | ((m_byData[Offset+2] & 0xff) << 16) | ((m_byData[Offset+3] & 0xff) << 24);
		return result;
	}
	
	public short DataShort(int Offset)
	{
		Offset += m_Base;
		int result = m_byData[Offset] & 0xff | ((m_byData[Offset+1] & 0xff) << 8);
		return (short)result;
	}
	
	public byte DataByte(int Offset)
	{
		Offset += m_Base;
		int result = m_byData[Offset];
		return (byte)result;
	}
	
	public void WriteInt(int Offset, int Value)
	{
		Offset += m_Base;
		m_byData[Offset + 0] = (byte)(Value & 0xff);
		m_byData[Offset + 1] = (byte)((Value >> 8) & 0xff);
		m_byData[Offset + 2] = (byte)((Value >> 16) & 0xff);
		m_byData[Offset + 3] = (byte)((Value >> 24) & 0xff);
	}
	
	public void WriteShort(int Offset, short Value)
	{
		Offset += m_Base;
		m_byData[Offset + 0] = (byte)(Value & 0xff);
		m_byData[Offset + 1] = (byte)((Value >> 8) & 0xff);
	}
	
	public long DataQuad(int Offset) {
		Offset += m_Base;
		long result = m_byData[Offset] & 0xff | 
				((m_byData[Offset+1] & 0xff) << 8) | 
				((m_byData[Offset+2] & 0xff) << 16) | 
				((m_byData[Offset+3] & 0xff) << 24) | 
				((m_byData[Offset+4] & 0xff) << 32) | 
				((m_byData[Offset+5] & 0xff) << 40) | 
				((m_byData[Offset+6] & 0xff) << 48) | 
				((m_byData[Offset+7] & 0xff) << 56);
		return result;
	}
	
	public void WriteQuad(int Offset, long Value) {
		Offset += m_Base;
		m_byData[Offset + 0] = (byte)(Value & 0xff);
		m_byData[Offset + 1] = (byte)((Value >> 8) & 0xff);
		m_byData[Offset + 2] = (byte)((Value >> 16) & 0xff);
		m_byData[Offset + 3] = (byte)((Value >> 24) & 0xff);
		m_byData[Offset + 4] = (byte)((Value >> 32) & 0xff);
		m_byData[Offset + 5] = (byte)((Value >> 40) & 0xff);
		m_byData[Offset + 6] = (byte)((Value >> 48) & 0xff);
		m_byData[Offset + 7] = (byte)((Value >> 56) & 0xff);
	}
	
}
