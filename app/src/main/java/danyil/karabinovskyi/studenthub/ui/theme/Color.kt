package danyil.karabinovskyi.studenthub.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import danyil.karabinovskyi.studenthub.R

val Shadow11 = Color(0xff001787)
val Shadow10 = Color(0xff00119e)
val Shadow9 = Color(0xff0009b3)
val Shadow8 = Color(0xff0200c7)
val Shadow7 = Color(0xff0e00d7)
val Shadow6 = Color(0xff2a13e4)
val Shadow5 = Color(0xff4b30ed)
val Shadow4 = Color(0xff7057f5)
val Shadow3 = Color(0xff9b86fa)
val Shadow2 = Color(0xffc8bbfd)
val Shadow1 = Color(0xffded6fe)
val Shadow0 = Color(0xfff4f2ff)

val Ocean11 = Color(0xff005687)
val Ocean10 = Color(0xff006d9e)
val Ocean9 = Color(0xff0087b3)
val Ocean8 = Color(0xff00a1c7)
val Ocean7 = Color(0xff00b9d7)
val Ocean6 = Color(0xff13d0e4)
val Ocean5 = Color(0xff30e2ed)
val Ocean4 = Color(0xff57eff5)
val Ocean3 = Color(0xff86f7fa)
val Ocean2 = Color(0xffbbfdfd)
val Ocean1 = Color(0xffd6fefe)
val Ocean0 = Color(0xfff2ffff)

val Lavender11 = Color(0xff170085)
val Lavender10 = Color(0xff23009e)
val Lavender9 = Color(0xff3300b3)
val Lavender8 = Color(0xff4400c7)
val Lavender7 = Color(0xff5500d7)
val Lavender6 = Color(0xff6f13e4)
val Lavender5 = Color(0xff8a30ed)
val Lavender4 = Color(0xffa557f5)
val Lavender3 = Color(0xffc186fa)
val Lavender2 = Color(0xffdebbfd)
val Lavender1 = Color(0xffebd6fe)
val Lavender0 = Color(0xfff9f2ff)

val Rose11 = Color(0xff7f0054)
val Rose10 = Color(0xff97005c)
val Rose9 = Color(0xffaf0060)
val Rose8 = Color(0xffc30060)
val Rose7 = Color(0xffd4005d)
val Rose6 = Color(0xffe21365)
val Rose5 = Color(0xffec3074)
val Rose4 = Color(0xfff4568b)
val Rose3 = Color(0xfff985aa)
val Rose2 = Color(0xfffdbbcf)
val Rose1 = Color(0xfffed6e2)
val Rose0 = Color(0xfffff2f6)

val MineShaft = Color(0xff363636)
val MineShaft_Bar = Color(0xff2b2b2b)

val Neutral8 = Color(0xff121212)
val Neutral7 = Color(0xde000000)
val Neutral6 = Color(0x99000000)
val Neutral5 = Color(0x61000000)
val Neutral4 = Color(0x1f000000)
val Neutral3 = Color(0x1fffffff)
val Neutral2 = Color(0x61ffffff)
val Neutral1 = Color(0xbdffffff)
val Neutral0 = Color(0xffffffff)

val FunctionalRed = Color(0xffd00036)
val FunctionalRedDark = Color(0xffea6d7e)
val FunctionalGreen = Color(0xff52c41a)
val FunctionalGrey = Color(0xfff6f6f6)
val FunctionalDarkGrey = Color(0xff2e2e2e)

val BlueDianne = Color(0xff1a2c3a)

const val AlphaNearOpaque = 0.95f

/*
    BLUE LIGHT PALETTE
 */
val Blue500 = Color(0xFF2196f3)  // Primary
val BLUE800 = Color(0xFF0277BD)  // PrimaryVariant
val CYAN500 = Color(0xFF00bcd4)  // Secondary
val CYAN700 = Color(0xff008ba3)  // SecondaryVariant
val LIGHTBLUE50 = Color(0xffe1f5fe)  // Background
val LIGHTBLUE100 = Color(0xffb3e5fc)  // Surface
val RED600 = Color(0xffe53935)  //Error

/*
    BLUE DARK PALETTE
 */
val BLUE900 = Color(0xFF0d47a1)  // Primary
val BLUE950 = Color(0xFF002171)  // PrimaryVariant
val CYAN900 = Color(0xFF006064)  // Secondary
val CYAN800 = Color(0xff428e92)  // SecondaryVariant
val BLUEGREY900 = Color(0xff000a12)  // Background
val BLUEGREY800 = Color(0xff263238)  // Surface
val RED800 = Color(0xffba000d)  //Error


val DarkGray = Color(0xFF202020)
val MediumGray = Color(0xFF404040)
val LightGray = Color(0xFF606060)
val TextWhite = Color(0xFFEEEEEE)
val HintGray = Color(0xFF6D6D6D)
val TextGray = Color(0xFFA6A6A6)
val GreenAccent = Color(0xFF08FF04)
val DarkerGreen = Color(0xFF029600)

@Immutable
data class Colors(
    val textHighEmphasis: Color,
    val textLowEmphasis: Color,
    val disabled: Color,
    val borders: Color,
    val inputBackground: Color,
    val background: Color,
    val onBackground: Color,
    val secondaryBackground: Color,
    val buttonBackgroundPrimary: Color,
    val barsContent: Color,
    val iconPrimary: Color,
    val iconDisabled: Color,
    val iconAfterAction: Color,
    val linkBackground: Color,
    val overlay: Color,
    val overlayDark: Color,
    val primaryAccent: Color,
    val errorAccent: Color,
    val infoAccent: Color,
    val highlight: Color,
    val ownMessagesBackground: Color,
    val otherMessagesBackground: Color,
    val deletedMessagesBackground: Color,
    val giphyMessageBackground: Color,
    val threadSeparatorGradientStart: Color,
    val threadSeparatorGradientEnd: Color,
    val primary: Color,
    val black: Color,
) {

    companion object {

        @Composable
        fun defaultColors(): Colors = Colors(
            textHighEmphasis = Neutral1,
            textLowEmphasis = Neutral2,
            disabled = Neutral3,
            borders = colorResource(R.color.borders),
            inputBackground = colorResource(R.color.input_background),
            background = colorResource(R.color.app_background),
            onBackground = Neutral3,
            secondaryBackground = MineShaft,
            barsContent = MineShaft_Bar,
            buttonBackgroundPrimary = Ocean11,
            linkBackground = colorResource(R.color.link_background),
            overlay = BlueDianne,
            overlayDark = colorResource(R.color.overlay_dark),
            primaryAccent = colorResource(R.color.primary_accent),
            errorAccent = colorResource(R.color.error_accent),
            infoAccent = colorResource(R.color.info_accent),
            highlight = colorResource(R.color.highlight),
            ownMessagesBackground = Ocean8,
            otherMessagesBackground = colorResource(R.color.borders),
            deletedMessagesBackground = colorResource(R.color.input_background),
            giphyMessageBackground = colorResource(R.color.bars_background),
            threadSeparatorGradientStart = colorResource(R.color.input_background),
            threadSeparatorGradientEnd = colorResource(R.color.app_background),
            primary = Neutral0,
            iconPrimary = Neutral0,
            iconDisabled = Neutral3,
            iconAfterAction = Shadow3,
            black = Neutral8,
        )

        @Composable
        fun defaultDarkColors(): Colors = Colors(
            textHighEmphasis = Neutral1,
            textLowEmphasis = Neutral2,
            disabled = Neutral3,
            borders = colorResource(R.color.borders_dark),
            inputBackground = colorResource(R.color.input_background_dark),
            background = Neutral8,
            onBackground = Neutral3,
            secondaryBackground = MineShaft,
            barsContent = MineShaft_Bar,
            buttonBackgroundPrimary = Ocean11,
            linkBackground = colorResource(R.color.link_background_dark),
            overlay = BlueDianne,
            overlayDark = colorResource(R.color.overlay_dark_dark),
            primaryAccent = colorResource(R.color.primary_accent_dark),
            errorAccent = colorResource(R.color.error_accent_dark),
            infoAccent = colorResource(R.color.info_accent_dark),
            highlight = colorResource(R.color.highlight_dark),
            ownMessagesBackground = Ocean10,
            otherMessagesBackground = colorResource(R.color.borders_dark),
            deletedMessagesBackground = colorResource(R.color.input_background_dark),
            giphyMessageBackground = colorResource(R.color.bars_background_dark),
            threadSeparatorGradientStart = colorResource(R.color.input_background_dark),
            threadSeparatorGradientEnd = colorResource(R.color.app_background_dark),
            primary = Neutral0,
            iconPrimary = Neutral0,
            iconDisabled = Neutral3,
            iconAfterAction = Shadow3,
            black = Neutral8,
        )
    }
}
