import impl.Command;

/**
 *
 *   日志类
 */
public class PlaceOrderDecorator implements Command {

    public void execute() {
        System.out.println("执行支付");
    }
}

/**
 * 性能统计
 */
 class CommandProxy implements Command {
    Command cmd;

    public CommandProxy(Command cmd) {
        this.cmd = cmd;
    }

    public void execute() {
        System.out.println("记录日志start");
        this.cmd.execute();
        System.out.println("记录日志end");
    }
}

class Client {
    public static void main(String[] args) {
        Command cmd = new CommandProxy(new PlaceOrderDecorator());
        cmd.execute();
    }
}
