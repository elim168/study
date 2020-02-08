import socket
import threading

port = 8888
class Server(threading.Thread):

    def run(self):
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        server_socket.bind(('', port))
        recv_data, client_address = server_socket.recvfrom(1024)
        recv_data = recv_data.decode()
        print('Udp Server receive data[%s] from [%s]' % (recv_data, client_address))
        server_socket.sendto('Response Data From Server'.encode(), client_address)
        server_socket.close()

class Client(threading.Thread):
    def run(self):
        client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        client_socket.sendto('Hello Udp Server'.encode(), ('', port))
        recv_data, server_address = client_socket.recvfrom(1024)
        print('Udp Client receive data[%s] from [%s]' % (recv_data.decode(), server_address))
        client_socket.close()


server = Server()
server.start()
client = Client()
client.start()


# output
'''
Udp Server receive data[Hello Udp Server] from [('127.0.0.1', 55339)]
Udp Client receive data[Response Data From Server] from [('127.0.0.1', 8888)]
'''