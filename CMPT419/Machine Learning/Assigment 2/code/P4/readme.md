# Readme for cifar_finetune.py 

> Khizr Ali Pardhan 301314376 march 6th 

I choose option #2, “Run validation of the model every few training epochs on validation or test set of the dataset and save the model with the best validation error” 

my best model is the file called `checkpoint`, I validated every epoch, since computational expense is not an issue for me.  

I have only 1 code file, which is the modification of the file given. 

What I did is 

* Added GPU-cuda support

* Have train and test sets 

  * 4 worker threads 

* Printed loss only once per epoch, makes reading easier

* Made a copy of the provided training loop, which I repurposes for validating. I save the model’s `state_dict` is it has a new lowest validation score.

  *  The following code snippet is preferred, but is saves more than ‘just the model’. I commented it out, since its beyond the scope of the question.

  ```python
  checkpoint = { 
       'epoch': epoch + 1,
       'running_loss_val_best': running_loss,
       'state_dict': model.state_dict(),
       'optimizer': optimizer.state_dict()
  }
  ```


### Sample loss, per epoch, built-in train:test split

```
Loss for the 1 epoch is 1876.819
New best test-set loss!:  311.743756711483
Loss for the 2 epoch is 1495.451
New best test-set loss!:  298.12937861680984
Loss for the 3 epoch is 1458.475
New best test-set loss!:  292.45646703243256
Loss for the 4 epoch is 1436.071
New best test-set loss!:  290.7603438794613
Loss for the 5 epoch is 1426.200
New best test-set loss!:  287.5481992661953
```

