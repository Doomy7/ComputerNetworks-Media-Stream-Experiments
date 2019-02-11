import ffmpy

if __name__ == '__main__':
    source_folder = 'SOURCE VIDEO FILES/'
    source_video = 'DJ Bill Gates - Windows error version.mp4'
    target_folder = 'VIDEO FILES/'
    target_file = 'djbill.avi'
    ff = ffmpy.FFmpeg(inputs={source_folder+source_video: None}, outputs={target_folder+target_file: None})
    ff.run()
    #ffmpeg -i input -c:v libx265 -crf 28 -c:a aac -b:a 128k output.mp4
