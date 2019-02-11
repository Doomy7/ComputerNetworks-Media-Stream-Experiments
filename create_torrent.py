from dottorrent import Torrent

t = Torrent('VIDEO FILES/djbill265.mp4', trackers=['http://tracker.openbittorrent.com:80/announce'])
t.generate()
with open('mydata.torrent', 'wb') as f:
        t.save(f)
