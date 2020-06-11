package Utils;
import ObjectProtocol.ClientObjectWorker;
import Service.IService;

import java.net.Socket;

public class ObjectConcurrentServer extends AbstractConcurrentServer{
    private IService service;
    public ObjectConcurrentServer(Integer port, IService service) {
        super(port);
        this.service = service;
        System.out.println("ObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientObjectWorker worker = new ClientObjectWorker(service, client);
        return new Thread(worker);
    }
}
