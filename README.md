# 高性能微信朋友圈
众所周知，微信朋友圈的列表一直以来被众多研究性能问题的朋友拿来作为模范，对于其实现方式，一直以来有点难以望其项背的感觉。只能默默的感叹微信的开发者是真的牛逼。经过一段时间的研究，现在我将带领大家以全新的认知对微信朋友圈的结构进行分析，并通过自己的方式加以实现。
先上图：![](https://github.com/KCrason/HighPerformanceFriendsCircle/blob/master/app/apk/20180503_222857.gif)

GIF看着有点卡，可以下载apk自行体验，流畅度和微信几乎无差别：https://github.com/KCrason/HighPerformanceFriendsCircle/blob/master/app/apk/app-debug.apk

我们都知道，在Android中，对于列表的而言，要避免其卡顿，可以从以下几个角度进行优化。

- 减少布局层级，避免过多的Item View的无用布局嵌套。
- 对于有图片的列表，要在滑动时对图片加以控制，即滑动时不加载图片，停止滑动之后再加载图片。
- 应当避免在Adapter的填充数据时做过多的计算，或者嵌套过多的逻辑判断。对于复杂的计算结果应当在Adapter填充数据之前计算完成。
- 其他方面的优化则是尽管在数据Bean中完成对各种数据变换的操作，包括复杂的计算，比如将String转换成需要的SpannableStringBuilder等。
- 最后就是除了要减少onMeasure()和onLayout()的次数之后，我们也需要减少View的创建。减少View的创建我们可以使用一个弱引用的缓存数组和实现View对象的缓存，这里要感谢[razerdp](https://github.com/razerdp)提供的思路。

具体的一些其他逻辑，代码中自行研究吧，后续可能还会继续更新该项目，包括表情的匹配，电话号码的匹配等，看自己时间情况。欢迎大家start！
