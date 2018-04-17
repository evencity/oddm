
/**
 * 定义枚举类型
 * @author lgx
 */
public enum Gender {
	
	/**
	 * 保密
	 */
	secrecy(1), 
	/**
	 * 男
	 */
	male(2), 
	/**
	 * 女
	 */
	female(3);
    
    private final int value;
    
    private Gender(int num) {
        this.value = num+1;
    }
    
    public int toValue() {
        return value;
    }
}