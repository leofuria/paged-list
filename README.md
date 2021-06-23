# paged-list

## Installation

The Components are available through [GitHub Packages][github.releases], you only need to download and add the project with Gradle dependency.

### Import with Gradle

Import the Paged List library by adding it to your `build.gradle` file.
For example:
```groovy
allprojects {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/leofuria/pagedlist")
        }
    }
}
```
```groovy
implementation "br.com.bitsolutions:pagedlist:1.0.0"
```

## Components

To use the component, just add the view to the layout and follow the examples in [Example][component.example].

```xml
<br.com.bitsolutions.pagedlist.view.PagedListLayout
        android:id="@+id/pagedListRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

## Common Issues

- Our layout uses the features below, so if you want to replace those features, you need to apply them.

```xml
<resources>
    <string name="paged_list_header">Header</string>
    <string name="paged_list_load_end">No more results</string>

    <!--ERROR LAYOUT-->
    <string name="paged_list_generic_error">Sorry, an error occurred</string>
    <string name="paged_list_desc_error_image">Error icon</string>
    <string name="paged_list_try_again_label">Try again</string>
    <string name="paged_list_verify_connection_label">Check your internet connection and try again</string>
</resources>
```
```xml
<resources>
    <color name="paged_list_primary_color">#143A7C</color>
    <color name="paged_list_primary_color_light">#1265C0</color>
    <color name="paged_list_secondary_color">#E6207E</color>

    <color name="paged_list_gray">#9e9e9e</color>
    <color name="paged_list_gray_dark">#616161</color>
    <color name="paged_list_gray_darker">#212121</color>

    <color name="paged_list_icon_gray">@color/paged_list_gray</color>
</resources>
```
```xml
<resources>
    <style name="paged_list_title_error_layout">
        <item name="android:textColor">@color/paged_list_gray_dark</item>
        <item name="android:fontFamily">@font/montserrat_bold</item>
        <item name="android:textSize">@dimen/paged_list_sp_16</item>
    </style>

    <style name="paged_list_desc_error_layout">
        <item name="android:textColor">@color/paged_list_gray_darker</item>
        <item name="android:fontFamily">@font/montserrat_regular</item>
        <item name="android:textSize">@dimen/paged_list_sp_14</item>
    </style>

    <style name="paged_list_header_view_holder_layout">
        <item name="android:textColor">@color/paged_list_gray_darker</item>
        <item name="android:fontFamily">@font/montserrat_regular</item>
        <item name="android:textSize">@dimen/paged_list_sp_14</item>
    </style>

    <style name="paged_list_footer_view_holder_layout">
        <item name="android:textColor">@color/paged_list_gray_darker</item>
        <item name="android:fontFamily">@font/montserrat_regular</item>
        <item name="android:textSize">@dimen/paged_list_sp_14</item>
    </style>

    <style name="paged_list_button_error_layout">
        <item name="android:background">@drawable/paged_list_button_primary</item>
        <item name="android:textAllCaps">false</item>
        <item name="android:fontFamily">@font/montserrat_medium</item>
        <item name="android:textColor">@android:color/white</item>
        <item name="android:textSize">@dimen/paged_list_sp_14</item>
    </style>

    <style name="paged_list_error_view_holder_button">
        <item name="android:textColor">@color/paged_list_primary_color</item>
        <item name="drawableStartCompat">@drawable/paged_list_ic_baseline_refresh_24</item>
        <item name="android:drawableStart">@drawable/paged_list_ic_baseline_refresh_24</item>
        <item name="android:background">@null</item>
        <item name="colorControlNormal">@color/paged_list_primary_color</item>
        <item name="colorControlActivated">@color/paged_list_primary_color</item>
    </style>

    <style name="paged_list_error_image">
        <item name="android:background">@null</item>
        <item name="colorControlNormal">@color/paged_list_gray</item>
        <item name="colorControlActivated">@color/paged_list_gray</item>
    </style>
</resources>
```

[github.releases]: https://github.com/leofuria?tab=packages&repo_name=paged-list
[component.example]: https://github.com/leofuria/paged-list/tree/master/app/src/main/java/br/com/bitsolutions/android