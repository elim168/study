import socket
import threading

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(('', 8899))  # localhost:8899
server_socket.listen()

client_sockets = []


def read_data(client_socket, client_address):
    while True:
        recv_data = client_socket.recv(1024)
        recv_data = recv_data.decode()
        print(recv_data)
        if recv_data == 'quit':
            client_sockets.remove(client_socket)
            client_socket.close()
            break
        for _socket in client_sockets:
            if _socket == client_socket:
                continue
            _socket.send('{} said: {}'.format(client_address, recv_data).encode())


while True:
    client_socket, client_address = server_socket.accept()
    client_sockets.append(client_socket)
    print('receive request from', client_address)
    t = threading.Thread(target=read_data, args=(client_socket, client_address))
    t.start()


