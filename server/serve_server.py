import socket


def Main():

    host = '127.0.0.1'
    port = 5000

    sock = socket.socket()
    sock.bind((host, port))

    sock.listen(5)
    c, addr = sock.accept()
    print(str(addr) + ' Connected !')

    while(True):
        data = c.recv(1024)
        if not data:
            break
        print(str(addr) + ' said ' + str(data))
        data = str(data).upper()
        print("reply :" + data)
        c.send(data.encode())
    sock.close()


if __name__ == '__main__':
    Main()
