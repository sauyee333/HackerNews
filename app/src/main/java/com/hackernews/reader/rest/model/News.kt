package com.hackernews.reader.rest.model

/**
 * Created by sauyee on 26/12/17.
 */

data class Story(
        val by: String, //f2n
        val descendants: Int, //194
        val id: Int, //16002068
        val kids: List<Int> ?,
        val score: Int, //348
        val time: Int, //1514162766
        val title: String, //NVIDIA GeForce driver deployment in datacenters is forbidden now
        val type: String, //story
        val url: String //http://www.nvidia.com/content/DriverDownload-March2009/licence.php?lang=us&type=GeForce
)

data class Comments(
        val by: String, //_nalply
        val id: Int, //16007744
        val kids: List<Int>,
        val parent: Int, //16000389
        val text: String, //I&#x27;m Deaf and read lips.<p>Thanks for this extremely intriguing idea to use emoji to depict a talking person visually.<p>You know, some animated films get it right. Sometimes I clearly see utterances like «Thank you!», «Okay», but more often not. Especially older animations don&#x27;t care and just let the character move the mouth in very simplistic ways: «Babbabbabaa Baababba ba baaa.»<p>Similarly for that emoji. It is moving the mouth in quite arbitrary ways. It looks like: «Sobbabbabee be &lt;grin&gt; seebee &lt;grin&gt; &lt;frown&gt; babbaa».<p>To solve this problem we need about twelve emoji for utterances: «ah», «ay», «ee», «oh», «oo», «b», «d», «f», «l», «n», «r» and «s». The «r» emoji must be animated (so we see the trilling tongue). The other sounds are either invisible like «k» or cover several different sounds like «b» also covering «m» and «p».<p>«Okay» would be rendered by: «oh», «oo», «oo» (standing for «k» wich is invisible) «ay» and «ee».<p>Edit: Add «th». This is an extremely simple sound to lipread. I remember my delight when I learnt English in my youth and in an movie suddenly realised that an actor said «Thank you».
        val time: Int, //1514277142
        val type: String //comment
)
