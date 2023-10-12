package com.example.trial.merge_file


class FFmpegQueryExtension {


    fun addImageOnVideo(inputVideo: String, imageInput: String, output: String): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        inputs.apply {
            add("-i")
            add(inputVideo)
            add("-i")
            add(imageInput)
            add("-filter_complex")
            add("[1][0]scale2ref[i][m];[m][i]overlay[v]")
            add("-map")
            add("[v]")
            add("-map")
            add("0:a?")
            add("-ac")
            add("2")
            add(output)
        }

        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }

    fun addImageOnVideoBottom(
        inputVideo: String, imageInput: String, posX: Float?, posY: Float?, output: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        inputs.apply {
            add("-i")
            add(inputVideo)
            add("-i")
            add(imageInput)
            add("-filter_complex")
            add("overlay=(main_w-overlay_w):(main_h-overlay_h)")
            add("-preset")
            add("ultrafast")
            add(output)
        }
        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }
//    fun addTextOnVideo(inputVideo: String, textInput: String, posX: Float?, posY: Float?, fontPath: String, isTextBackgroundDisplay: String, fontSize: Int, fontcolor: String, output: String): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        var borderQuery = ""
////        if (isTextBackgroundDisplay) {
//        borderQuery = ":box=1:boxcolor=$isTextBackgroundDisplay:boxborderw=0"
////        }
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-vf")
//            add("drawtext=text='$textInput':fontfile=$fontPath:x=$posX:y=$posY:fontsize=$fontSize:fontcolor=$fontcolor${borderQuery.trim()}")
//            add("-c:a")
//            add("copy")
//            add("-preset")
//            add("ultrafast")
//            add(output)
//        }
//        return inputs.toArray(arrayOfNulls<String>(inputs.size))
//    }

    //one text scroll
//fun addTextOnVideo(inputVideo: String, textInput: String,output: String): Array<String> {
//    val inputs: ArrayList<String> = ArrayList()

//    inputs.apply {
//        add("-i")
//        add(inputVideo)
//        add("-vf")
//        add("drawtext=fontfile=/system/fonts/DroidSans.ttf:text='SiteName hulluway':fontsize=40:fontcolor=white: x=w-(t-4.5)*(w+tw)/5.5:y=100")
//        add("-acodec")
//        add("copy")
//        add("-y")
//        add(output)
//
//    }
//    return inputs.toArray(arrayOfNulls<String>(inputs.size))
//}

    //text scroll in bottom
//    fun addTextOnVideo(inputVideo: String, textInput: String,output: String): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-vf")
//            add("drawtext=fontfile=/system/fonts/DroidSans.ttf:text='SiteName hulluway':fontsize=40:fontcolor=white:x=w-(t-4.5)*(w+tw)/2:y=h-th")
//            add("-acodec")
//            add("copy")
//            add("-y")
//            add(output)
//
//        }
//        return inputs.toArray(arrayOfNulls<String>(inputs.size))
//    }
//
//    fun addTextOnVideo(
//        inputVideo: String,
//        imageInput: String,
//        textInput: String,
//        output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//
//
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[1][0]scale2ref[i][m];[m][i]overlay[v]")
//            add("-map")
//            add("[v]")
//            add("-map")
//            add("0:a?")
//            add("-ac")
//            add("2")
//            add("-s")
//            add("1920x1080")
//            add("drawtext=text='$textInput':fontfile=$fontPath:fontsize=24:fontcolor=white")
//            add("-c:a")
//            add("copy")
//            add("-preset")
//            add("ultrafast")
//            add(output)
//
//        }
//        return inputs.toArray(arrayOfNulls<String>(inputs.size))
//    }


    //text , image add on video success
//    fun addTextOnVideo(
//        inputVideo: String,
//        imageInput: String,
//        textInput: String,
//        output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[0:v][1:v]overlay=10:10,drawtext=text='$textInput':fontfile=$fontPath:fontsize=24:fontcolor=white")
//            add("-c:a")
//            add("copy")
//            add("-movflags")
//            add("+faststart")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }


    //text scroll in horizontal and image add on video
//    fun addTextOnVideo(
//        inputVideo: String,
//        imageInput: String,
//        textInput: String,
//        output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[0:v][1:v]overlay=10:10,drawtext=fontfile=/system/fonts/DroidSans.ttf:text='SiteName hulluway':fontsize=40:fontcolor=white:x=w-(t-4.5)*(w+tw)/2:y=h-th")
//            add("-c:a")
//            add("copy")
//            add("-movflags")
//            add("+faststart")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }


    //video size  for youtube size and text scroll in bottom with image
//    fun addTextOnVideo(
//        inputVideo: String,
//        imageInput: String,
//        textInput: String,
//        output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[0:v][1:v]overlay=(W-w)/2:(H-h)/2,drawtext=fontfile=/system/fonts/DroidSans.ttf:text='SiteName hulluway':fontsize=15:fontcolor=black:x=w-(t-4.5)*(w+tw)/2:y=h-th")
//            add("-s")
//            add("1920x1080")
//            add("-c:a")
//            add("copy")
//            add("-movflags")
//            add("+faststart")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }


    //try continue
//    fun addTextOnVideo(
//        inputVideo: String, imageInput: String, textInput: String, output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[1][0]scale2ref[i][m];[m][i]overlay[v],drawtext=fontfile=/system/fonts/DroidSans.ttf:text=$textInput:fontsize=15:fontcolor=black:x=w-(t-4.5)*(w+tw)/2:y=h-th")
//            add("-map")
//            add("[v]")
//            add("-map")
//            add("0:a?")
//            add("-ac")
//            add("2")
//            add("-c:a")
//            add("copy")
//            add("-movflags")
//            add("+faststart")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }

    //two text add on video
//    fun addTextOnVideo(
//        inputVideo: String, imageInput: String, textInput: String, output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-vf")
//            add("drawtext=text='Stack Overflow':fontcolor=white: borderw=2: fontfile=$fontPath: fontsize=w*0.04:x=(w-text_w)-(w*0.04): y=(h-text_h)-(w*0.04),drawtext=text='Stack Overflow': fontcolor=white: borderw=2:fontfile=$fontPath: fontsize=w*0.04: x=(w-text_w)/2: y=(h-text_h)/2")
//            add("-codec:a")
//            add("copy")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }

    // add three text add on video
//    fun addTextOnVideo(
//        inputVideo: String, imageInput: String, textInput: String, output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-vf")
//            add("drawtext=text='$textInput':fontfile=$fontPath:x=(w-text_w)/2:y=h-th-10:fontsize=20:fontcolor=white,drawtext=text=sanjay:fontfile=$fontPath:x=(w-text_w)/2:y=h-th-30:fontsize=20:fontcolor=white,drawtext=text=dangar:fontfile=$fontPath:x=(w-text_w)/2:y=h-th-50:fontsize=20:fontcolor=white")
//            add("-c:a")
//            add("copy")
//            add("-preset")
//            add("ultrafast")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }


    //three text scroll bottom add on video
//    fun addTextOnVideo(
//        inputVideo: String, imageInput: String, textInput: String, output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-vf")
//            add("drawtext=fontfile=/system/fonts/DroidSans.ttf:text='$textInput':fontsize=20:fontcolor=white:x=w-(t-4.5)*(w+tw)/2:y=h-th-10,drawtext=fontfile=/system/fonts/DroidSans.ttf:text='$textInput':fontsize=20:fontcolor=white:x=w-(t-4.5)*(w+tw)/2:y=h-th-30,drawtext=fontfile=/system/fonts/DroidSans.ttf:text='$textInput':fontsize=20:fontcolor=white:x=w-(t-4.5)*(w+tw)/2:y=h-th-50")
//            add("-c:a")
//            add("copy")
//            add("-preset")
//            add("ultrafast")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }


    //three text scroll bottom and set padding vertical and set text scroll position  and text scroll speed with image add on video
//    fun addTextOnVideo(
//        inputVideo: String, imageInput: String, textInput: String, output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//        var textInput2="sanjay dangar"
//        var textInput3="Goti Infoways is best company "
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[0:v][1:v]overlay=(W-w)/2:(H-h)/2,drawtext=fontfile=/system/fonts/DroidSans.ttf:text='$textInput':fontsize=20:fontcolor=black:x=w-(t-4.5)*100/2:y=h-th-0,drawtext=fontfile=/system/fonts/DroidSans.ttf:text='$textInput2':fontsize=20:fontcolor=white:x=w-(t-4.5)*100/2:y=h-th-30,drawtext=fontfile=/system/fonts/DroidSans.ttf:text='$textInput3':fontsize=20:fontcolor=white:x=w-(t-4.5)*100/2:y=h-th-70")
//            add("-s")
//            add("1920x1080")
//            add("-c:a")
//            add("copy")
//            add("-movflags")
//            add("+faststart")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }

    //two text scroll,text scroll speed,image  size set on video and video size  youtube size
//    fun addTextOnVideo(
//        tvInputPathVideo: String,
//        tvInputPathImage: String,
//        textInputeCenter: String,
//        textInputeBottom: String,
//        videoWidth: Int,
//        videoHeight: Int,
//        outputPath: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//
////        var textRepoterName = "drawtext=fontfile='$fontPath':text='$textInputeRepoter':fontsize=20:fontcolor=white:x=210:y=h-th-120"
////        var textRepoterName = "drawtext=fontfile='$fontPath':text='$textInputeRepoter':fontsize=20:fontcolor=black:x=$RepoterOnScreenX:y=$RepoterOnScreenY"
//        var textCenter = "drawtext=fontfile='$fontPath':text='$textInputeCenter':fontsize=20:fontcolor=white:x=w-(t-4.5)*100/2:y=h-th-60"
//        val textBottom =
//            "drawtext=fontfile='$fontPath':text='$textInputeBottom':fontsize=20:fontcolor=black:x=w-(t-4.5)*100/2:y=h-th-25"
//
//
//
//        inputs.apply {
//            add("-i")
//            add(tvInputPathVideo)
//            add("-i")
//            add(tvInputPathImage)
//            add("-filter_complex")
//            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0, $textCenter, $textBottom")
//            add("-c:a")
//            add("copy")
//            add("-s")
//            add("1920x1080")
//            add("-movflags")
//            add("+faststart")
//            add(outputPath)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }

    //looping of left to right  scrolling text
//    fun addTextOnVideo(
//        tvInputPathVideo: String,
//        tvInputPathImage: String,
//        textInputeCenter: String,
//        textInputeBottom: String,
//        videoWidth: Int,
//        videoHeight: Int,
//        outputPath: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//
//
//        var textCenter = "drawtext=fontfile='$fontPath':text='$textInputeCenter':fontsize=20:fontcolor=white:x=(mod(5*n\\,w+tw)-tw):y=h-th-60"
//        val textBottom = "drawtext=fontfile='$fontPath':text='$textInputeBottom':fontsize=20:fontcolor=black:x=(mod(5*n\\,w+tw)-tw):y=h-th-25"
//
//
//
//        inputs.apply {
//            add("-i")
//            add(tvInputPathVideo)
//            add("-i")
//            add(tvInputPathImage)
//            add("-filter_complex")
//            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0, $textCenter, $textBottom")
//            add("-c:a")
//            add("copy")
//            add("-s")
//            add("1920x1080")
//            add("-movflags")
//            add("+faststart")
//            add(outputPath)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }

   // looping of right  to left  scrolling text
    fun addTextOnVideo(
        tvInputPathVideo: String,
        tvInputPathImage: String,
        textInputeCenter: String,
        textInputeBottom: String,
        videoWidth: Int,
        videoHeight: Int,
        outputPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        val fontPath = "/system/fonts/DroidSans.ttf"


        var textCenter = "drawtext=fontfile='$fontPath':text='$textInputeCenter':fontsize=20:fontcolor=white:x=w-(mod(5*n\\,w+tw)):y=h-th-60"
        val textBottom = "drawtext=fontfile='$fontPath':text='$textInputeBottom':fontsize=20:fontcolor=black:x=w-(mod(5*n\\,w+tw)):y=h-th-25"



        inputs.apply {
            add("-i")
            add(tvInputPathVideo)
            add("-i")
            add(tvInputPathImage)
            add("-filter_complex")
            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0, $textCenter, $textBottom")
            add("-c:a")
            add("copy")
            add("-s")
            add("1920x1080")
            add("-movflags")
            add("+faststart")
            add(outputPath)
        }

        return inputs.toArray(arrayOfNulls(inputs.size))
    }

//    fun addTextOnVideo(
//        inputVideo: String,
//        imageInput: String,
//        textInput: String,
//        output: String,
//        videoWidth: Int,
//        videoHeight: Int
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//        var textInput2 = "sanjay dangar"
//        var textInput3 = "Goti Infoways is best company"
//        var textCenter =
//            "drawtext=fontfile='$fontPath':text='$textInput2':fontsize=20:fontcolor=white:x=w-(t-4.5)*100/2:y=h-th-60"
//        val textBottom =
//            "drawtext=fontfile='$fontPath':text='$textInput':fontsize=20:fontcolor=black:x=w-(t-4.5)*100/2:y=h-th-25"
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[0][1]overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2,drawtext=text='$textInput':x=10:y=10:fontsize=24:fontcolor=white")
//            add("-c:a")
//            add("copy")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }

    //set image size youtube size on video
//    fun addTextOnVideo(
//        inputVideo: String, imageInput: String, textInput:  String, output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//        var textInput2 = "sanjay dangar"
//        var textInput3 = "Goti Infoways is best company"
//        var textCenter =
//            "drawtext=fontfile='$fontPath':text='$textInput2':fontsize=20:fontcolor=white:x=w-(t-4.5)*100/2:y=h-th-60"
//        val textBottom =
//            "drawtext=fontfile='$fontPath':text='$textInput':fontsize=20:fontcolor=black:x=w-(t-4.5)*100/2:y=h-th-25"
//
//        val outputWidth = 1920 // YouTube 1080p width
//
//        val outputHeight = 1080
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[1:v]scale=" + outputWidth + ":" + outputHeight + "[scaled_image];[0:v][scaled_image]overlay=(W-w)/2:(H-h)/2")
//            add("-c:a")
//            add("copy")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }

//    fun addTextOnVideo(
//        inputVideo: String, imageInput: String, textInput:  String, output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//        var textInput2 = "sanjay dangar"
//        var textInput3 = "Goti Infoways is best company"
//        var textCenter =
//            "drawtext=fontfile='$fontPath':text='$textInput2':fontsize=20:fontcolor=white:x=w-(t-4.5)*100/2:y=h-th-60"
//        val textBottom =
//            "drawtext=fontfile='$fontPath':text='$textInput':fontsize=20:fontcolor=black:x=w-(t-4.5)*100/2:y=h-th-25"
//
//
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[1:v][0:v]scale2ref=oh*mdar:ih[img][video];[video][img]overlay=(W-w)/2:(H-h)/2")
//            add("-c:a")
//            add("copy")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls(inputs.size))
//    }

//    fun addTextOnVideo(
//        inputVideo: String,
//        imageInput: String,
//        textInput: String,
//        output: String
//    ): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        val fontPath = "/system/fonts/DroidSans.ttf"
//        var textInput2 = "sanjay dangar"
//        var textInput3 = "Goti Infoways is best company"
//        var textCenter =
//            "drawtext=fontfile='$fontPath':text='$textInput2':fontsize=20:fontcolor=white:x=w-(t-4.5)*100/2:y=h-th-60"
//        val textBottom =
//            "drawtext=fontfile='$fontPath':text='$textInput3':fontsize=20:fontcolor=black:x=w-(t-4.5)*100/2:y=h-th-25"
//
//
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[1][0]scale2ref[i][m];[m][i]overlay[v];[v]drawtext=fontfile='$fontPath':text='$textInput2':fontsize=20:fontcolor=white:x=w-(t-4.5)*100/2:y=h-th-60")
//            add("-map")
//            add("[v]")
//            add("-map")
//            add("-movflags")
//            add("+faststart")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls<String>(inputs.size))
//    }

    fun addGifOnVideo(
        inputVideo: String, tvGifPath: String, textInput: String, output: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()

        inputs.apply {
            add("-i")
            add(inputVideo)
            add("-ignore_loop")
            add("0")
            add("-i")
            add("tenor.gif")
            add("-filter_complex")
            add("[1:v]format=yuva444p,scale=80:80,setsar=1,rotate=PI/6:c=black@0:ow=rotw(PI/6):oh=roth(PI/6) [rotate];[0:v][rotate] overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2:shortest=1")
            add("-codec:a")
            add("copy")
            add("-y")
            add(output)
        }

        return inputs.toArray(arrayOfNulls(inputs.size))
    }

    fun addVideoWaterMark(
        inputVideo: String, imageInput: String, posX: Float?, posY: Float?, output: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        inputs.apply {
            add("-i")
            add(inputVideo)
            add("-i")
            add(imageInput)
            add("-filter_complex")
            add("[1:v]scale=65:65 [watermark]; [0:v][watermark]overlay=(main_w-overlay_w)-10:90")
            add("-preset")
            add("ultrafast")
            add(output)
        }
        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }
}