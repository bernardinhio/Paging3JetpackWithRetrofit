I am learning this Library Paging3 since some weekends, originally I don't know about Paging2 but this new version is very advanced. I am in the process of learning it so there is a lot of Log.d because I am tracking and counting the creating of Items and pages and tracking the LoadState changes in 2 places: In the RecyclerView and outside.

Inside the RecyclerView I have implemented footer and Header. These are 2 views added when we scoll down (the footer) and we scroll up (the Header). They can show the change in the LoadState such as in case of LoadState.Loading and LoadState.Error.

I throw Errors with custom messages according to cases and then I modify the UI of the footer or header (such as background color or text or button to retry). The cases of Errors that I designed are: End of the list of Items (not really an error but it returns a footer LoadResult.Error  instead of LoadResult.Page)
Another Error is IOException meaning the Internet is broken
And one is that initial call to retrofit has resulted in no Results
And one is for HttpException representing anything in the Retrofit Api and I called the message Thrown “Server is broken”

The Library also allows us to addLoadStateListener{} which I can use to modify something in the UI for example when the App starts and I need to hide / unhide recyclerView if in case there is no initial results at all and I can show instead of RecyclerView, a Text and ProgressBar and Button to retry.

So here are the first screenshots:

1. Initial Loading when no page has loaded yet
![1  Initial Loading when no page has loaded yet](https://user-images.githubusercontent.com/20923486/132661277-11f6f43c-8b45-403d-b198-7502de5a56be.jpg)

2. Footer when loading Pages
![2  Footer when loading Pages](https://user-images.githubusercontent.com/20923486/132661419-f306e3f5-b1db-4e5e-97b6-2f13771a4502.jpg)

3. Footer when Internet Error
![3  Footer when Internet Error](https://user-images.githubusercontent.com/20923486/132661521-c6e258fc-8b93-4abc-9574-51529b0c7b99.jpg)

4. Footer when end of the List of the last page
![4  Footer when end of the List of the last page](https://user-images.githubusercontent.com/20923486/132661631-4717e649-2397-4957-8681-62ddf4a274ee.jpg)

5. Header when loading Page
![5  Header when loading Page](https://user-images.githubusercontent.com/20923486/132661744-2bbc953d-ca57-43e2-8e37-3c53f16d3a40.jpg)

6. Header when Internet Error
![6  Header when Internet Error](https://user-images.githubusercontent.com/20923486/132661829-506b5265-b345-48a7-b13a-a0073da54938.jpg)


