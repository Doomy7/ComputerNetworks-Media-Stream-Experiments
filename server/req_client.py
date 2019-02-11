import socket


def Main():

    host = '127.0.0.1'
    port = 5000

    sock = socket.socket()
    sock.connect((host, port))

    message = input("-> ")
    while(message) != 'q':
        sock.send(message.encode())
        data = sock.recv(1024)
        print(str(data))
        message = input("-> ")
    sock.close()


if __name__ == '__main__':
    Main()
