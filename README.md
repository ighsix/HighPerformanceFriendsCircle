# HighPerformanceFriendsCircle
众所周知，微信朋友圈的列表一直以来被众多研究性能问题的朋友拿来作为模范，对于其实现方式，一直以来有点难以望其项背的感觉。只能默默的感叹微信的开发者是真的牛逼。经过一段时间的研究，现在我将带领大家以全新的认知对微信朋友圈的结构进行分析，并通过自己的方式加以实现。
先上图：![20180503_222857.gif](https://upload-images.jianshu.io/upload_images/1860505-8598f3a9a5664f11.gif?imageMogr2/auto-orient/strip)

GIF看着有点卡，可以下载apk自行体验，流畅度和微信几乎无差别：https://github.com/KCrason/HighPerformanceFriendsCircle/blob/master/app/apk/app-debug.apk

我们都知道，在Android中，对于列表的而言，要避免其卡顿，可以从以下几个角度进行优化。

1、减少布局层级，避免过多的Item View的无用布局嵌套。
2、对于有图片的列表，要在滑动时对图片加以控制，即滑动时不加载图片，停止滑动之后再加载图片。
3、应当避免在Adapter的填充数据时做过多的计算，或者嵌套过多的逻辑判断。对于复杂的计算结果应当在Adapter填充数据之前计算完成。

以上这些都是针对一个普通的Adapter所基本的一些优化，而对于微信朋友圈这种复杂列表，除了以上几种之外，还需要对其进行其他方面的优化。例如包括减少View的重复创建，构建缓存View，以及减少布局的onMeasure和onLayout次数。这些都尤为重要。下面我们先简单分析一些微信的列表每一项的视图结构，通过分析微信，我们可以参悟到一些自己的解决思路。

首先我们通过Android Device Monitor视图分析器来分析微信朋友圈的每一个Item的视图结构。
![](https://upload-images.jianshu.io/upload_images/1860505-6800010e2781abc4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
从图中我们可以知道，它的Item最外部是一个FramLayout，里面的内容都是一些常见的View，关键的地方在于评论列表的构建，我们知道，微信的评论是直接在列表内展示的，在这里我们发现它的评论列表其实就是一个用一个LinearLayout进行包裹的。而由于每条动态评论的不确定性，我们需要在adapter中不断的创建评论View和移除多余的View，就这涉及到性能问题，那么微信到底是如何在一个Item中展示多条评论又不出现明显的卡顿现象呢？

在ViewGroup中，有个一方法可能一直被大家所忽略，它就是addViewInLayout()，由于它是protected声明的，所以外部的ViewGroup的子类无法直接调用，而要使用addViewInLayout()，必须继承ViewGroup或ViewGroup的子类并重写该方法。那么addViewInLayout()方法究竟有何用呢？它和我们常用的addView()又有什么区别呢？

我们先来看官方对addViewInLayout()的解释：
![](https://upload-images.jianshu.io/upload_images/1860505-5e985d11432b27d3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> 简单翻译：在布局中添加视图。 如果在onLayout()方法中，您需要添加更多视图（例如列表视图），这非常有用。 如果索引是负数，则意味着将其放在列表的末尾。

这似乎没什么特殊的，但是它真正的有用的地方在于该方法中的`preventRequestLayout`参数，这是一个boolean类型的值，但是它确实及其有用的。如果为true，在添加View时他将不会触发子对象的布局请求。也就是说添加View时不会触发onMeasure和onLayout操作。官方api解释图：
![H](https://upload-images.jianshu.io/upload_images/1860505-2a05a13a31873eda.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


通过对addViewInLayout()的分析，我想你大概明白了，既然动态添加View的时候可以不用触发onMeasure()和onLayout()，那将大量的节约adapter的刷新速度。上面我们有提到过，对adapter的性能要点中，减少adapter Item的onMeasure和onLayout尤为重要（因为事实上View的显示onMeasure和onLayout需要耗费大量的时间）。

同样的，在移除View时，我们可以使用removeViewInLayout()，它有和addViewInLayout()一样的效果。因此，通过这个办法，我们解决了评论列表的动态变化更新的性能问题。

而九宫格图片的展示只需要自定义ViewGroup即可实现，其内部依然是对ImageView的添加和移除，同样的我们可以使用该方法addViewInLayout()和removeViewInLayout()来减少onMeasure()和onLayout()的次数以节省性能开支。

其他方面的优化则是尽管在数据Bean中完成对各种数据变换的操作，包括复杂的计算，比如将String转换成需要的SpannableStringBuilder等。

最后就是除了要减少onMeasure()和onLayout()的次数之后，我们也需要减少View的创建。减少View的创建我们可以使用一个弱引用的缓存数组和实现View对象的缓存，这里要感谢[razerdp](https://github.com/razerdp)提供的思路。

具体的一些其他逻辑，代码中自行研究吧，后续可能还会继续更新该项目，包括表情的匹配，电话号码的匹配等，看自己时间情况。欢迎大家start！
